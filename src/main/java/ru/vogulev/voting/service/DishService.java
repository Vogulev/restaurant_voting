package ru.vogulev.voting.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import ru.vogulev.voting.model.Dish;
import ru.vogulev.voting.repository.DishRepository;
import ru.vogulev.voting.repository.RestaurantRepository;

import java.util.List;
import java.util.Optional;

import static ru.vogulev.voting.util.validation.ValidationUtil.*;

@Slf4j
@Service("dishService")
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
@AllArgsConstructor
public class DishService {

    private RestaurantRepository restaurantRepository;
    private DishRepository dishRepository;

    public Dish create(Dish dish, int restaurantId) {
        log.info("create {}", dish);
        checkNew(dish);
        dish.setRestaurant(restaurantRepository.getById(restaurantId));
        return dishRepository.save(dish);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        dishRepository.deleteExisted(id);
    }

    public Optional<Dish> get(int id) {
        log.info("get {}", id);
        return dishRepository.findById(id);
    }

    public List<Dish> getAll(int restaurantId) {
        log.info("getAll");
        return dishRepository.findAllByRestaurantIdOrderByAddDate(restaurantId);
    }

    public void update(Dish dish, int id) {
        log.info("update {} with id={}", dish, id);
        assureIdConsistent(dish, id);
        dishRepository.save(dish);
    }
}
