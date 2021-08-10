package ru.dlukin.restaurant_voting.testdata;

import ru.dlukin.restaurant_voting.MatcherFactory;
import ru.dlukin.restaurant_voting.model.Role;
import ru.dlukin.restaurant_voting.model.User;

import java.util.Collections;

public class UserTestData {
    public static final MatcherFactory.Matcher<User> MATCHER =
            MatcherFactory.usingIgnoringFieldsComparator(User.class, "registered");

    public static final int USER_ID = 1;
    public static final int ADMIN_ID = USER_ID + 3;
    public static final int NOT_FOUND_ID = 100;

    public static final User user =
            new User(USER_ID, "User1_Name", "user1@gmail.com", "password", Role.USER);
    public static final User user2 =
            new User(USER_ID + 1, "User2_Name", "user2@gmail.com", "password", Role.USER);
    public static final User user3 =
            new User(USER_ID + 2, "User3_Name", "user3@gmail.com", "password", Role.USER);
    public static final User admin =
            new User(ADMIN_ID, "Admin1_Name", "admin@restaurant.com", "admin", Role.ADMIN, Role.USER);

    public static User getNew() {
        return new User(null, "New", "new@gmail.com", "newPass", Role.USER);
    }

    public static User getUpdated() {
        User updated = new User(user);
        updated.setName("UpdatedName");
        updated.setEmail("update@gmail.com");
        updated.setPassword("newPass");
        updated.setRoles(Collections.singletonList(Role.ADMIN));
        return updated;
    }
}
