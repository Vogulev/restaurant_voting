package ru.vogulev.voting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.vogulev.voting.HasId;
import ru.vogulev.voting.model.NamedEntity;

import java.beans.ConstructorProperties;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends NamedEntity implements HasId {

    long voteCount;

    @ConstructorProperties({"id", "name", "voteCount"})
    public RestaurantTo(Integer id, String name, long voteCount) {
        super(id, name);
        this.voteCount = voteCount;
    }
}
