package ru.dlukin.restaurant_voting.testdata;

import ru.dlukin.restaurant_voting.MatcherFactory;
import ru.dlukin.restaurant_voting.model.Restaurant;

public class RestaurantTestData {
    public static final MatcherFactory.Matcher<Restaurant> MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Restaurant.class, "dishes", "votes");

    public static final int KFC_ID = 1;
    public static final int MCDONALDS_ID = 2;
    public static final int NOT_FOUND_ID = 100;

    public static final Restaurant kfc = new Restaurant(KFC_ID, "KFC", null, null);
    public static final Restaurant mcDonalds = new Restaurant(MCDONALDS_ID, "McDonalds", null, null);

    public static Restaurant getNew() {
        return new Restaurant(null, "New Restaurant", null, null);
    }

    public static Restaurant getUpdated() {
        Restaurant updated = new Restaurant(kfc);
        updated.setName("UpdatedNameRestaurant");
        return updated;
    }
}
