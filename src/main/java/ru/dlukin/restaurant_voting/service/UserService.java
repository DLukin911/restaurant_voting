package ru.dlukin.restaurant_voting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.repository.UserRepository;
import ru.dlukin.restaurant_voting.to.UserTo;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.UserUtil.*;
import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository repository;

    public User create(User user) {
        Assert.notNull(user, "User must not be null");
        return repository.save(user);
    }

    public User get(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found entity with id " + id));
    }

    public List<User> getAll() {
        return repository.findAll();
    }

    public void update(User user) {
        Assert.notNull(user, "User must not be null");
        prepareToSave(user);
        repository.save(user);
    }

    @Transactional
    public void update(UserTo userTo) {
        Assert.notNull(userTo, "UserTo must not be null");
        User user = updateFromTo(get(userTo.getId()), userTo);
        prepareToSave(user);
        repository.save(user);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public User findByEmail(String email) {
        return repository.findByEmailIgnoreCase(email).orElseThrow(() -> new EntityNotFoundException("Not found entity with email "
                + email));
    }

    public User findByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Not found entity with name "
                + name));
    }
}
