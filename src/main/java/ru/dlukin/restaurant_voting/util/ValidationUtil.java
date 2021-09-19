package ru.dlukin.restaurant_voting.util;

import lombok.experimental.UtilityClass;
import ru.dlukin.restaurant_voting.HasId;
import ru.dlukin.restaurant_voting.util.exception.IllegalRequestDataException;
import ru.dlukin.restaurant_voting.util.exception.NotFoundException;

import java.util.Optional;

@UtilityClass
public class ValidationUtil {

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must be new (id = null)");
        }
    }

    //  Conservative when you reply, but accept liberally (http://stackoverflow.com/a/32728226/548473)
    public static void assureIdConsistent(HasId bean, int id) {
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.id() != id) {
            throw new IllegalRequestDataException(bean.getClass().getSimpleName() + " must has id = " + id);
        }
    }

    public static void checkModification(int count, int id) {
        if (count == 0) {
            throw new NotFoundException("Entity with id = " + id + " not found");
        }
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id = " + id);
    }

    public static void checkNotFoundWithIdAndRestaurantId(boolean found, int id, int restaurantId) {
        checkNotFound(found, "id = " + id + " and Restaurant id = " + restaurantId);
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static <T> T checkNotFoundOptional(Optional<T> object, String msg) {
        return object.orElseThrow((() ->
                new NotFoundException("Not found entity with " + msg)));
    }
}