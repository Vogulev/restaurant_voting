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
import ru.vogulev.voting.model.Restaurant;
import ru.vogulev.voting.repository.RestaurantRepository;
import ru.vogulev.voting.to.RestaurantTo;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static ru.vogulev.voting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.vogulev.voting.util.validation.ValidationUtil.checkNew;

@Slf4j
@Service("restaurantService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@AllArgsConstructor
@CacheConfig(cacheNames = "restaurants")
@Transactional(readOnly = true)
public class RestaurantService {

    private final RestaurantRepository repository;

    @Transactional
    @CacheEvict(allEntries = true)
    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        checkNew(restaurant);
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    public Optional<RestaurantTo> get(int id) {
        log.info("get {}", id);
        return repository.findRestaurantWithVotesById(id);
    }

    @Cacheable
    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    @Transactional
    @CacheEvict(allEntries = true)
    public void update(Restaurant restaurant, int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }

    @Cacheable
    public List<RestaurantTo> getAllOnDate(LocalDate voteDate) {
        log.info("get all restaurants on date {}", voteDate);
        return repository.findAllRestaurantsWithVotesCountOnDate(voteDate);
    }
}
