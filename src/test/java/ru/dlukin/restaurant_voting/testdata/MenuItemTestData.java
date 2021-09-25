package ru.dlukin.restaurant_voting.testdata;

import ru.dlukin.restaurant_voting.MatcherFactory;
import ru.dlukin.restaurant_voting.model.MenuItem;

import java.time.LocalDate;

import static ru.dlukin.restaurant_voting.testdata.RestaurantTestData.*;

public class MenuItemTestData {
    public static final MatcherFactory.Matcher<MenuItem> MENU_ITEM_MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(MenuItem.class, "restaurant");

    public static final int MENU_ITEM_ID = 1;
    public static final int NOT_FOUND_ID = 100;

    public static final MenuItem MENU_ITEM_1 = new MenuItem(MENU_ITEM_ID, "Chicken Basket", LocalDate.now(), 250, kfc);
    public static final MenuItem MENU_ITEM_2 = new MenuItem(MENU_ITEM_ID + 1, "Chicken Combo", LocalDate.now(), 150, kfc);
    public static final MenuItem MENU_ITEM_3 = new MenuItem(MENU_ITEM_ID + 2, "Sandwich", LocalDate.now(), 200, kfc);
    public static final MenuItem MENU_ITEM_4 = new MenuItem(MENU_ITEM_ID + 3, "Big Mac", LocalDate.now(), 170, mcDonalds);
    public static final MenuItem MENU_ITEM_TEST_DATE = new MenuItem(MENU_ITEM_ID + 5, "Burger", LocalDate.of(2021, 9, 1),
            100, burgerKing);

    public static MenuItem getNew() {
        return new MenuItem(null, "New MenuItem", LocalDate.now(), 500, kfc);
    }

    public static MenuItem getUpdated() {
        MenuItem updated = new MenuItem(MENU_ITEM_1);
        updated.setName("UpdatedNameMenuItem");
        updated.setDate(LocalDate.MAX);
        updated.setPrice(1000);
        updated.setRestaurant(mcDonalds);
        return updated;
    }
}
