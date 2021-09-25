package ru.dlukin.restaurant_voting.util;

import lombok.experimental.UtilityClass;
import ru.dlukin.restaurant_voting.model.MenuItem;
import ru.dlukin.restaurant_voting.to.MenuItemTo;

@UtilityClass
public class MenuItemUtil {

    public static MenuItem createNewFromTo(MenuItemTo menuItemTo) {
        return new MenuItem(null, menuItemTo.getName(), menuItemTo.getDate(), menuItemTo.getPrice(), null);
    }

    public static MenuItem updateFromTo(MenuItem menuItem, MenuItemTo menuItemTo) {
        menuItem.setName(menuItemTo.getName());
        menuItem.setDate(menuItemTo.getDate());
        menuItem.setPrice(menuItemTo.getPrice());
        return menuItem;
    }
}