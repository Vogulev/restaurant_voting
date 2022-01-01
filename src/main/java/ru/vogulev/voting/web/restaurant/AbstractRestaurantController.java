package ru.vogulev.voting.web.restaurant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import ru.vogulev.voting.model.Restaurant;
import ru.vogulev.voting.service.RestaurantService;

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
