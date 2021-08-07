package ru.dlukin.restaurant_voting.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class UserService {

    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

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
        repository.save(user);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Not found entity with " +
                "email " + email));
    }

    public User findByName(String name) {
        return repository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Not found entity with name "
                + name));
    }
}
