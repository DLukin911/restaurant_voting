package ru.dlukin.restaurant_voting.util;

import lombok.experimental.UtilityClass;
import ru.dlukin.restaurant_voting.model.Dish;
import ru.dlukin.restaurant_voting.to.DishTo;

@UtilityClass
public class DishUtil {

    public static Dish createNewFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getName(), dishTo.getDateVote(), dishTo.getPrice(), null);
    }

    public static Dish updateFromTo(DishTo dishTo) {
        return new Dish(dishTo.getId(), dishTo.getName(), dishTo.getDateVote(), dishTo.getPrice(),
                dishTo.getRestaurant());
    }
}