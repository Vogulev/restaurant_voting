package ru.vogulev.voting.web.vote;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.vogulev.voting.model.Vote;
import ru.vogulev.voting.service.VoteService;
import ru.vogulev.voting.to.VoteTo;

import java.time.LocalDate;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Vote admin", description = "The Vote Admin API")
@ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Ok"),
        @ApiResponse(responseCode = "400", description = "Bad request"),
        @ApiResponse(responseCode = "500", description = "Server error")})
public class AdminVoteController {

    static final String REST_URL = "/api/admin/votes";

    protected VoteService service;

    @Operation(summary = "Get history by restaurant id", tags = "vote admin")
    @GetMapping("/restaurant/{restaurantId}")
    public List<VoteTo> getAllByRestaurantId(@PathVariable int restaurantId) {
        return service.getAllByRestaurantId(restaurantId);
    }

    @Operation(summary = "Get all votes for today", tags = "vote admin")
    @GetMapping("/today")
    public List<VoteTo> getAllOnToday() {
        return service.getAllByDate(LocalDate.now());
    }

    @Operation(summary = "Get all votes on date", tags = "vote admin")
    @GetMapping
    public List<VoteTo> getAllOnDate(@RequestParam LocalDate date) {
        return service.getAllByDate(date);
    }

    @Operation(summary = "Get history by user id", tags = "vote admin")
    @GetMapping("/user/{userId}")
    public List<Vote> getAllByUserId(@PathVariable int userId) {
        return service.getAllByUserId(userId);
    }
}
