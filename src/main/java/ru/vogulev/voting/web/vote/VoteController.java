package ru.vogulev.voting.web.vote;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.vogulev.voting.model.Vote;
import ru.vogulev.voting.service.VoteService;
import ru.vogulev.voting.web.AuthUser;

import java.net.URI;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = VoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Vote", description = "The Vote API")
public class VoteController {

    static final String REST_URL = "api/profile/votes";

    protected VoteService service;

    @Operation(summary = "Get by id", tags = "vote")
    @GetMapping("/{id}")
    public ResponseEntity<Vote> get(@PathVariable int id,
                                    @AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity.of(service.get(id, authUser.id()));
    }

    @Operation(summary = "Get all auth user votes", tags = "vote")
    @GetMapping
    public List<Vote> getAll(@AuthenticationPrincipal AuthUser authUser) {
        return service.getAllByUserId(authUser.id());
    }

    @Operation(summary = "Delete by id", tags = "vote")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id, @AuthenticationPrincipal AuthUser authUser) {
        service.delete(id, authUser.id());
    }

    @Operation(summary = "Update", tags = "vote")
    @PutMapping(value = "/{restaurantId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        service.update(restaurantId, authUser.id());
    }

    @Operation(summary = "Create", tags = "vote")
    @PostMapping(value = "/{restaurantId}")
    public ResponseEntity<Vote> createWithLocation(@PathVariable int restaurantId,
                                                   @AuthenticationPrincipal AuthUser authUser) {
        Vote created = service.create(restaurantId, authUser);
        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{restaurantId}/{id}")
                .buildAndExpand(restaurantId, created.getId()).toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }
}
