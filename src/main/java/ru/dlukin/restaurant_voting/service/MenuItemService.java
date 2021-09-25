package ru.dlukin.restaurant_voting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.dlukin.restaurant_voting.model.MenuItem;
import ru.dlukin.restaurant_voting.repository.MenuItemRepository;
import ru.dlukin.restaurant_voting.repository.RestaurantRepository;

import java.time.LocalDate;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNotFoundOptional;
import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNotFoundWithIdAndRestaurantId;

@Service
@AllArgsConstructor
public class MenuItemService {

    private final MenuItemRepository menuItemRepository;

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public MenuItem create(MenuItem menuItem, int restaurantId) {
        Assert.notNull(menuItem, "MenuItem must not be null");
        menuItem.setRestaurant(restaurantRepository.getById(restaurantId));
        return menuItemRepository.save(menuItem);
    }

    @Transactional
    public void update(MenuItem menuItem, int restaurantId) {
        Assert.notNull(menuItem, "MenuItem must not be null");
        menuItem.setRestaurant(restaurantRepository.getById(restaurantId));
        menuItemRepository.save(menuItem);
    }

    public void delete(int id, int restaurantId) {
        checkNotFoundWithIdAndRestaurantId(menuItemRepository.deleteByIdAndRestaurantId(id, restaurantId) != 0, id,
                restaurantId);
    }

    public MenuItem get(int id) {
        return checkNotFoundOptional(menuItemRepository.findById(id), "id = " + id);
    }

    public List<MenuItem> getAllByRestaurantId(int restaurantId) {
        return menuItemRepository.findAllByRestaurantId(restaurantId);
    }

    public List<MenuItem> getAllByRestaurantIdAndDate(int restaurantId, LocalDate date) {
        return menuItemRepository.findAllByRestaurantIdAndDate(restaurantId, date);
    }

    public MenuItem getByIdAndRestaurantId(int id, int restaurantId) {
        return checkNotFoundOptional(menuItemRepository.findByIdAndRestaurantId(id, restaurantId), "id = " + id +
                " and restaurantId " + restaurantId);
    }
}
