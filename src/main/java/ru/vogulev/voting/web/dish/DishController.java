package ru.vogulev.voting.web.dish;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vogulev.voting.model.Dish;
import ru.vogulev.voting.service.DishService;
import ru.vogulev.voting.web.restaurant.RestaurantController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Tag(name = "Restaurant", description = "The Dish API")
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Server error")})
public class DishController {

    private final DishService service;

    @Operation(summary = "Get menu", tags = "dish")
    @GetMapping("/{restaurantId}/dishes")
    public List<Dish> getMenuForToday(@PathVariable int restaurantId) {
        return service.getFromRestaurantByDate(restaurantId, LocalDate.now());
    }
}
