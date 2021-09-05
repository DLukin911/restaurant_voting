package ru.dlukin.restaurant_voting.testdata;

import ru.dlukin.restaurant_voting.MatcherFactory;
import ru.dlukin.restaurant_voting.model.Role;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.to.UserTo;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered", "password");

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = USER_ID + 3;
    public static final User admin = new User(ADMIN_ID, "Admin1_Name", "admin@restaurant.com", "{noop}admin",
            Role.USER, Role.ADMIN);
    public static final int NOT_FOUND_ID = 100;
    public static final User user = new User(USER_ID, "User1_Name", "user1@gmail.com", "{noop}password", Role.USER);
    public static final User user2 = new User(USER_ID + 1, "User2_Name", "user2@gmail.com", "{noop}password",
            Role.USER);
    public static final User user3 = new User(USER_ID + 2, "User3_Name", "user3@gmail.com", "{noop}password",
            Role.USER);
    public static final UserTo userTo = new UserTo(USER_ID, "UpdatedName", "update@gmail.com", "{noop}newPass");

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "{noop}newPass", Role.USER);
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setName("UpdatedName");
        updated.setEmail("update@gmail.com");
        updated.setPassword("{noop}newPass");
        return updated;
    }
}
