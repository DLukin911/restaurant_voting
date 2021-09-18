package ru.dlukin.restaurant_voting.util;

import lombok.experimental.UtilityClass;
import ru.dlukin.restaurant_voting.model.Dish;
import ru.dlukin.restaurant_voting.to.DishTo;

@UtilityClass
public class DishUtil {

    public static Dish createNewFromTo(DishTo dishTo) {
        return new Dish(null, dishTo.getName(), dishTo.getDate(), dishTo.getPrice(), null);
    }

    public static Dish updateFromTo(Dish dish, DishTo dishTo) {
        dish.setName(dishTo.getName());
        dish.setDate(dishTo.getDate());
        dish.setPrice(dishTo.getPrice());
        return dish;
    }
}