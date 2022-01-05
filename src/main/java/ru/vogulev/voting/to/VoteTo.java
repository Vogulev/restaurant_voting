package ru.vogulev.voting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.vogulev.voting.HasId;

import javax.validation.constraints.NotNull;
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
    String restaurantName;

    @NotNull
    Integer votedUserId;

    @NotNull
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
