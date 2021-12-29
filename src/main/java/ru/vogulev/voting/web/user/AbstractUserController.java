package ru.vogulev.voting.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import ru.vogulev.voting.model.User;
import ru.vogulev.voting.repository.UserRepository;
import ru.vogulev.voting.service.UserService;
import ru.vogulev.voting.util.UserUtil;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    protected UserService service;

    @Autowired
    private UniqueMailValidator emailValidator;

    @InitBinder
    protected void initBinder(WebDataBinder binder) {
        binder.addValidators(emailValidator);
    }

    public ResponseEntity<User> get(int id) {
        log.info("get {}", id);
        return ResponseEntity.of(service.get(id));
    }

    @CacheEvict(value = "users", allEntries = true)
    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    protected User prepareAndSave(User user) {
        return service.create(user);
    }
}