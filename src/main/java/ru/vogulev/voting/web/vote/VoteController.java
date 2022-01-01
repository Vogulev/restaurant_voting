package ru.vogulev.voting.web.vote;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.vogulev.voting.model.Vote;
import ru.vogulev.voting.service.VoteService;

import java.net.URI;
import java.util.List;

import static ru.vogulev.voting.web.SecurityUtil.authId;

@RestController
@AllArgsConstructor
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class VoteController {

    static final String REST_URL = "/api";

    protected VoteService service;

    @GetMapping("/profile/votes/{id}")
    public ResponseEntity<Vote> get(@PathVariable int id) {
        return ResponseEntity.of(service.get(id));
    }

    @GetMapping("/{restaurantId}/votes")
    public List<Vote> getAllByRestaurantId(@PathVariable int restaurantId) {
        return service.getAllByRestaurantId(restaurantId);
    }

    @GetMapping("/profile/votes")
    public List<Vote> getAll() {
        return service.getAllByUserId(authId());
    }

    @DeleteMapping("/profile/votes/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        service.delete(id);
    }

    @PutMapping(value = "/{restaurantId}/votes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId) {
        service.update(restaurantId);
    }

    @PostMapping(value = "/{restaurantId}/votes")
    public ResponseEntity<Vote> createWithLocation(@PathVariable int restaurantId) {
        Vote created = service.create(restaurantId);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}/votes/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
