package ru.dlukin.restaurant_voting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.model.Vote;
import ru.dlukin.restaurant_voting.repository.VoteRepository;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository repository;

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
        checkNotFoundWithId(repository.delete(id) != 0, id);
    }

    public List<Vote> findAllByDateVote(LocalDate dateVote) {
        return repository.findAllByDateVoteBetween(dateVote.atTime(LocalTime.MIN), dateVote.atTime(LocalTime.MAX));
    }

    public List<Vote> findAllByRestaurant(Restaurant restaurant) {
        return repository.findAllByRestaurant(restaurant);
    }

    public List<Vote> findAllByUser(User user) {
        return repository.findAllByUser(user);
    }

    public List<Vote> findAllByRestaurantAndDateVote(Restaurant restaurant, LocalDate dateVote) {
        return repository.findAllByRestaurantAndDateVoteBetween(restaurant, dateVote.atTime(LocalTime.MIN),
                dateVote.atTime(LocalTime.MAX));
    }
}
