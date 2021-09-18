package ru.dlukin.restaurant_voting.testdata;

import ru.dlukin.restaurant_voting.MatcherFactory;
import ru.dlukin.restaurant_voting.model.Dish;

import java.time.LocalDate;

import static ru.dlukin.restaurant_voting.testdata.RestaurantTestData.*;

public class DishTestData {
    public static final MatcherFactory.Matcher<Dish> DISH_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Dish.class, "restaurant");

    public static final int DISH_ID = 1;
    public static final int NOT_FOUND_ID = 100;

    public static final Dish dish1 = new Dish(DISH_ID, "Chicken Basket", LocalDate.now(), 250, kfc);
    public static final Dish dish2 = new Dish(DISH_ID + 1, "Chicken Combo", LocalDate.now(), 150, kfc);
    public static final Dish dish3 = new Dish(DISH_ID + 2, "Sandwich", LocalDate.now(), 200, kfc);
    public static final Dish dish4 = new Dish(DISH_ID + 3, "Big Mac", LocalDate.now(), 170, mcDonalds);
    public static final Dish dishTestDate = new Dish(DISH_ID + 5, "Burger", LocalDate.of(2021, 9, 1), 100, burgerKing);

    public static Dish getNew() {
        return new Dish(null, "New Dish", LocalDate.now(), 500, kfc);
    }

    public static Dish getUpdated() {
        Dish updated = new Dish(dish1);
        updated.setName("UpdatedNameDish");
        updated.setDate(LocalDate.MAX);
        updated.setPrice(1000);
        updated.setRestaurant(mcDonalds);
        return updated;
    }
}
