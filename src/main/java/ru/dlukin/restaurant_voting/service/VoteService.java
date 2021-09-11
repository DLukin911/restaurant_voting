package ru.dlukin.restaurant_voting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.model.Vote;
import ru.dlukin.restaurant_voting.repository.RestaurantRepository;
import ru.dlukin.restaurant_voting.repository.VoteRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNotFoundWithId;

@Service
@AllArgsConstructor
public class VoteService {

    private final VoteRepository voteRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Vote create(int restaurantId, User user) {
        LocalDateTime startToday = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
                LocalDate.now().getDayOfMonth(), 00, 00);
        LocalDateTime endToday = LocalDateTime.of(LocalDate.now().getYear(), LocalDate.now().getMonth(),
                LocalDate.now().getDayOfMonth(), 23, 59);

        Vote vote = new Vote(null, restaurantRepository.getById(restaurantId), user);
        List<Vote> oldVote = voteRepository.findAllByDateTimeVoteBetweenAndUser(startToday, endToday, user);
        if (oldVote.isEmpty()) {
            return voteRepository.save(vote);
        } else {
            if (vote.getDateTimeVote().isBefore(LocalDateTime.of(LocalDate.now().getYear(),
                    LocalDate.now().getMonth(), LocalDate.now().getDayOfMonth(), 11, 00))) {
                delete(oldVote.get(0).getId());
                return voteRepository.save(vote);
            }
            throw new IllegalArgumentException("The vote has already been counted, after 11:00 you cannot change" +
                    " the voting result");
        }
    }

    public List<Vote> getAll() {
        return voteRepository.findAll();
    }

    public void delete(int id) {
        checkNotFoundWithId(voteRepository.delete(id) != 0, id);
    }

    public List<Vote> getAllByRestaurant(int restaurantId) {
        return voteRepository.findAllByRestaurantId(restaurantId);
    }


    public List<Vote> getAllByRestaurantAndDateVote(int restaurantId, LocalDate dateVote) {
        return voteRepository.findAllByRestaurantIdAndDateTimeVoteBetween(restaurantId,
                dateVote.atTime(LocalTime.MIN), dateVote.atTime(LocalTime.MAX));
    }

    public Map<String, Integer> getRatingByDate(LocalDate dateVote) {
        List<Restaurant> restaurantList = restaurantRepository.findAllByDateVote(dateVote);
        Map<String, Integer> voteResult = new HashMap<>();
        for (Restaurant r : restaurantList) {
            voteResult.put(r.getName(), getAllByRestaurantAndDateVote(r.getId(), dateVote).size());
        }
        return voteResult;
    }
}
