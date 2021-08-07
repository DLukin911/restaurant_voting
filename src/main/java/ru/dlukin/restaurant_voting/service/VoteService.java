package ru.dlukin.restaurant_voting.service;

import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.model.Vote;
import ru.dlukin.restaurant_voting.repository.VoteRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Date;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNotFoundWithId;

@Service
public class VoteService {

    private final VoteRepository repository;

    public VoteService(VoteRepository repository) {
        this.repository = repository;
    }

    public Vote create(Vote vote) {
        Assert.notNull(vote, "Vote must not be null");
        return repository.save(vote);
    }

    public Vote get(int id) {
        return repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Not found entity with id " + id));
    }

    public List<Vote> getAll() {
        return repository.findAll();
    }

    public void update(Vote vote) {
        Assert.notNull(vote, "Vote must not be null");
        repository.save(vote);
    }

    public void delete(int id) {
        checkNotFoundWithId(repository.delete(id), id);
    }

    public List<Vote> findAllByDateVote(Date dateVote) {
        return repository.findAllByDateVote(dateVote);
    }

    public List<Vote> findAllByRestaurant(Restaurant restaurant) {
        return repository.findAllByRestaurant(restaurant);
    }

    public List<Vote> findAllByUser(User user) {
        return repository.findAllByUser(user);
    }

    public List<Vote> findAllByRestaurantAndDateVote(Restaurant restaurant, Date dateVote) {
        return repository.findAllByRestaurantAndDateVote(restaurant, dateVote);
    }
}
