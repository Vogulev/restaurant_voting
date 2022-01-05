package ru.vogulev.voting.web.dish;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vogulev.voting.model.Dish;
import ru.vogulev.voting.service.DishService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = DishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Tag(name = "Dish", description = "The Dish API")
public class DishController {

    static final String REST_URL = "api/restaurants/{restaurantId}/dishes";

    private DishService service;

    @Operation(summary = "Get menu", tags = "dish")
    @GetMapping("/today")
    public List<Dish> getMenuForToday(@PathVariable int restaurantId) {
        return service.getFromRestaurantByDate(restaurantId, LocalDate.now());
    }
}
