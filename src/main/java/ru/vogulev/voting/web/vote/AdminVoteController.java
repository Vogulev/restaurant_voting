package ru.vogulev.voting.web.vote;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.vogulev.voting.service.VoteService;
import ru.vogulev.voting.to.VoteTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Vote admin", description = "The Vote Admin API")
public class AdminVoteController {

    static final String REST_URL = "api/admin/votes";

    protected VoteService service;

    @Operation(summary = "Get by restaurant id", tags = "vote admin")
    @GetMapping("/{restaurantId}")
    public List<VoteTo> getAllByRestaurantId(@PathVariable int restaurantId) {
        return service.getAllByRestaurantId(restaurantId);
    }

    @Operation(summary = "Get all votes for today", tags = "vote admin")
    @GetMapping("/today")
    public List<VoteTo> getAllOnToday() {
        return service.getAllByDate(LocalDate.now());
    }

    @Operation(summary = "Get all votes for date", tags = "vote admin")
    @GetMapping
    public List<VoteTo> getAllOnDate(@RequestParam LocalDate date) {
        return service.getAllByDate(date);
    }
}
