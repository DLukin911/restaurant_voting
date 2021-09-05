package ru.dlukin.restaurant_voting.web.abstractcontroller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.service.UserService;
import ru.dlukin.restaurant_voting.to.UserTo;

import java.util.List;

import static ru.dlukin.restaurant_voting.util.UserUtil.createNewFromTo;
import static ru.dlukin.restaurant_voting.util.UserUtil.prepareToSave;
import static ru.dlukin.restaurant_voting.util.ValidationUtil.assureIdConsistent;
import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNew;

@Slf4j
public abstract class AbstractUserController {

    @Autowired
    private UserService service;

    public List<User> getAll() {
        log.info("getAll");
        return service.getAll();
    }

    public User get(int id) {
        log.info("get {}", id);
        return service.get(id);
    }

    public User create(User user) {
        log.info("create {}", user);
        checkNew(user);
        return service.create(user);
    }

    public User create(UserTo userTo) {
        User user = createNewFromTo(userTo);
        prepareToSave(user);
        return create(user);
    }

    public void delete(int id) {
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(User user, int id) {
        log.info("update {} with id={}", user, id);
        assureIdConsistent(user, id);
        service.update(user);
    }

    public void update(UserTo userTo, int id) {
        User user = createNewFromTo(userTo);
        update(user, id);
    }

    public User getByMail(String email) {
        log.info("findByEmail {}", email);
        return service.findByEmail(email);
    }

    public User getByName(String name) {
        log.info("getByName {}", name);
        return service.findByName(name);
    }
}