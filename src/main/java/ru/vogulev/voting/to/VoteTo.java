package ru.vogulev.voting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.vogulev.voting.HasId;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.beans.ConstructorProperties;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class VoteTo extends BaseTo implements HasId {

    @NotNull
    LocalDate voteDate;

    @NotNull
    Integer restaurantId;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100)
    String restaurantName;

    @NotNull
    Integer votedUserId;

    @NotNull
    @NotBlank
    @Size(min = 2, max = 100)
    String votedUserName;

    @ConstructorProperties({"id", "voteDate", "restaurantId", "restaurantName", "votedUserId", "votedUserName"})
    public VoteTo(Integer id, LocalDate voteDate, Integer restaurantId,
                  String restaurantName, Integer votedUserId, String votedUserName) {
        super(id);
        this.voteDate = voteDate;
        this.restaurantId = restaurantId;
        this.restaurantName = restaurantName;
        this.votedUserId = votedUserId;
        this.votedUserName = votedUserName;
    }
}
