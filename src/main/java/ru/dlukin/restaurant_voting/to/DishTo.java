package ru.dlukin.restaurant_voting.to;

import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.Value;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Value
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DishTo extends NamedTo {

    @NotNull
    LocalDate date = LocalDate.now();

    int price;

    public DishTo(Integer id, String name, int price) {
        super(id, name);
        this.price = price;
    }
}
