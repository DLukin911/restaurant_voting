package ru.dlukin.restaurant_voting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import ru.dlukin.restaurant_voting.HasId;

import java.io.Serializable;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class RestaurantTo extends NamedTo implements HasId, Serializable {
    public RestaurantTo(Integer id, String name) {
        super(id, name);
    }
}
