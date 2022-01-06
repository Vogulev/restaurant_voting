package ru.vogulev.voting.service;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.vogulev.voting.model.User;
import ru.vogulev.voting.repository.UserRepository;
import ru.vogulev.voting.util.exception.IllegalRequestDataException;

import java.util.List;
import java.util.Optional;

import static ru.vogulev.voting.util.UserUtil.prepareToSave;
import static ru.vogulev.voting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.vogulev.voting.util.validation.ValidationUtil.checkNew;

@Slf4j
@Service("userService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@AllArgsConstructor
@CacheConfig(cacheNames = "users")
public class UserService {

    private final UserRepository repository;

    @CacheEvict(value = "users", allEntries = true)
    public User create(User user) {
        log.info("create {}", user);
        Assert.notNull(user, "user must not be null");
        checkNew(user);
        return prepareAndSave(user);
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        repository.deleteExisted(id);
    }

    public Optional<User> get(int id) {
        return repository.findById(id);
    }

    @Cacheable("users")
    public List<User> getAll() {
        log.info("getAll");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name", "email"));
    }

    @CacheEvict(value = "users", allEntries = true)
    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        prepareAndSave(user);
    }

    public Optional<User> getByEmail(String email) {
        log.info("getByEmail {}", email);
        Assert.notNull(email, "email must not be null");
        return repository.getByEmail(email);
    }

    @CacheEvict(value = "users", allEntries = true)
    @Transactional
    public void enable(int id, boolean enabled) {
        log.info(enabled ? "enable {}" : "disable {}", id);
        User user = get(id).orElseThrow(() -> new IllegalRequestDataException("No user with id: " + id + " were found!"));
        user.setEnabled(enabled);
    }

    private User prepareAndSave(User user) {
        return repository.save(prepareToSave(user));
    }
}
