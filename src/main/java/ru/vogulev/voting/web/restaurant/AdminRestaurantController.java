package ru.vogulev.voting.web.restaurant;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.vogulev.voting.model.Restaurant;
import ru.vogulev.voting.service.RestaurantService;
import ru.vogulev.voting.to.RestaurantTo;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

import static ru.vogulev.voting.util.validation.ValidationUtil.assureIdConsistent;
import static ru.vogulev.voting.util.validation.ValidationUtil.checkNew;

@RestController
@RequestMapping(value = AdminRestaurantController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@AllArgsConstructor
@Tag(name = "Restaurant admin", description = "The Restaurant Admin API")
public class AdminRestaurantController {

    static final String REST_URL = "/api/admin/restaurants";

    protected RestaurantService service;

    @Operation(summary = "Get by id", tags = "restaurant admin")
    @GetMapping("/{id}")
    public ResponseEntity<RestaurantTo> get(@PathVariable int id) {
        return ResponseEntity.of(service.get(id));
    }

    @Operation(summary = "Delete by id", tags = "restaurant admin")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @Operation(summary = "Update", tags = "restaurant admin")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody Restaurant restaurant, @PathVariable int id) {
        assureIdConsistent(restaurant, id);
        service.update(restaurant, id);
    }

    @Operation(summary = "Create", tags = "restaurant admin")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Restaurant> createWithLocation(@Valid @RequestBody Restaurant restaurant) {
        checkNew(restaurant);
        Restaurant created = service.create(restaurant);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Operation(summary = "Get all with vote count", tags = "restaurant admin")
    @GetMapping("/count")
    public List<RestaurantTo> getAllWithVotesCountByDate(@RequestParam LocalDate voteDate) {
        return service.getAllOnDate(voteDate);
    }
}
