package ru.vogulev.voting.web.dish;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.vogulev.voting.model.Dish;
import ru.vogulev.voting.service.DishService;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminDishController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Tag(name = "Dish admin", description = "The Dish Admin API")
@ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Created"),
        @ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Server error")})
public class AdminDishController {

    public static final String REST_URL = "/api/admin/restaurants/{restaurantId}/dishes";

    private DishService service;

    @Operation(summary = "Get by id", tags = "dish admin")
    @GetMapping("/{id}")
    public ResponseEntity<Dish> get(@PathVariable int id, @PathVariable int restaurantId) {
        return ResponseEntity.of(service.get(id, restaurantId));
    }

    @Operation(summary = "Get menu history by restaurant id", tags = "dish admin")
    @GetMapping
    public List<Dish> getAll(@PathVariable int restaurantId) {
        return service.getAll(restaurantId);
    }

    @Operation(summary = "Delete", tags = "dish admin")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @PathVariable int restaurantId) {
        service.delete(id, restaurantId);
    }

    @Operation(summary = "Update", tags = "dish admin")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Dish dish, @PathVariable int id, @PathVariable int restaurantId) {
        service.update(dish, id, restaurantId);
    }

    @Operation(summary = "Create", tags = "dish admin")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Dish> createWithLocation(@Valid @RequestBody Dish dish, @PathVariable int restaurantId) {
        Dish created = service.create(dish, restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/restaurants/" + restaurantId + "/dishes/" + dish.id())
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

}
