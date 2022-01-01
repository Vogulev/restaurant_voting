package ru.vogulev.voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.vogulev.voting.model.Restaurant;
import ru.vogulev.voting.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

import static ru.vogulev.voting.util.validation.ValidationUtil.assureIdConsistent;

@Slf4j
@Service("restaurantService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@AllArgsConstructor
public class RestaurantService {

    private final RestaurantRepository repository;

    public Restaurant create(Restaurant restaurant) {
        log.info("create {}", restaurant);
        Assert.notNull(restaurant, "restaurant must not be null");
        return repository.save(restaurant);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        repository.deleteExisted(id);
    }

    public Optional<Restaurant> get(int id) {
        log.info("get {}", id);
        return repository.findById(id);
    }

    public List<Restaurant> getAll() {
        log.info("getAll");
        return repository.findAll(Sort.by(Sort.Direction.ASC, "name"));
    }

    public void update(Restaurant restaurant, int id) {
        log.info("update {} with id={}", restaurant, id);
        assureIdConsistent(restaurant, id);
        repository.save(restaurant);
    }
}
