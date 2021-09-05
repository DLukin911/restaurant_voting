package ru.dlukin.restaurant_voting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;
import org.hibernate.validator.constraints.Range;
import ru.dlukin.restaurant_voting.HasId;
import ru.dlukin.restaurant_voting.model.Restaurant;

import javax.validation.constraints.NotNull;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishTo extends NamedTo implements HasId, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    LocalDate dateVote;

    @NotNull
    @Range(min = 1, max = 5000)
    int price;

    @NotNull
    Restaurant restaurant;

    public DishTo(Integer id, String name, LocalDate dateVote, int price, Restaurant restaurant) {
        super(id, name);
        this.dateVote = dateVote;
        this.price = price;
        this.restaurant = restaurant;
    }
}
