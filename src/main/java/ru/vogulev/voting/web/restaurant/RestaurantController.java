package ru.vogulev.voting.web.restaurant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.vogulev.voting.model.Restaurant;
import ru.vogulev.voting.service.RestaurantService;

import java.util.List;

@RestController
@RequestMapping(value = RestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Tag(name = "Restaurant", description = "The Restaurant API")
@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Server error")})
public class RestaurantController {

    public static final String REST_URL = "/api/restaurants";

    protected RestaurantService service;

    @Operation(summary = "Get all", tags = "restaurant")
    @GetMapping
    public List<Restaurant> getAll() {
        return service.getAll();
    }
}
