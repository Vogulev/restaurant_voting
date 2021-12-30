package ru.vogulev.voting.web.restaurant;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import ru.vogulev.voting.model.Restaurant;
import ru.vogulev.voting.service.RestaurantService;

@Slf4j
public abstract class AbstractRestaurantController {

    @Autowired
    protected RestaurantService service;

    public ResponseEntity<Restaurant> get(int id) {
        return ResponseEntity.of(service.get(id));
    }

    public void delete(int id) {
        service.delete(id);
    }

}
