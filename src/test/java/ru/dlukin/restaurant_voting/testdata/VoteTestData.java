package ru.dlukin.restaurant_voting.testdata;

import ru.dlukin.restaurant_voting.MatcherFactory;
import ru.dlukin.restaurant_voting.model.Vote;

public class VoteTestData {
    public static final MatcherFactory.Matcher<Vote> MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(Vote.class, "dateTimeVote", "admin.registered", "user" +
                    ".registered", "restaurant");

    public static final int VOTE_ID = 1;
    public static final int NOT_FOUND_ID = 100;

    public static final Vote vote1 = new Vote(VOTE_ID, RestaurantTestData.mcDonalds, UserTestData.user);
    public static final Vote vote2 = new Vote(VOTE_ID + 1, RestaurantTestData.mcDonalds, UserTestData.user2);
    public static final Vote vote3 = new Vote(VOTE_ID + 2, RestaurantTestData.kfc, UserTestData.user3);

    public static Vote getNew() {
        return new Vote(null, RestaurantTestData.kfc, UserTestData.newUserForVote);
    }

    public static Vote getUpdated() {
        Vote updated = new Vote(vote1);
        updated.setRestaurant(RestaurantTestData.kfc);
        return updated;
    }
}
