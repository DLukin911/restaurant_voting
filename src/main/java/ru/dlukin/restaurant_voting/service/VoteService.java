package ru.dlukin.restaurant_voting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.model.Vote;
import ru.dlukin.restaurant_voting.repository.RestaurantRepository;
import ru.dlukin.restaurant_voting.repository.VoteRepository;
import ru.dlukin.restaurant_voting.util.exception.IllegalRequestDataException;
import ru.dlukin.restaurant_voting.util.exception.UpdateConflict;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

import static ru.dlukin.restaurant_voting.util.ValidationUtil.checkNotFoundOptional;

@Service
@AllArgsConstructor
public class VoteService {

    public static final LocalTime DEADLINE_TIME_VOTE = LocalTime.of(11, 0);

    private final VoteRepository voteRepository;

    private final RestaurantRepository restaurantRepository;

    @Transactional
    public Vote create(int restaurantId, User user) {
        Vote vote = new Vote(null, checkNotFoundOptional(restaurantRepository.findById(restaurantId),
                "restaurantId = " + restaurantId), user);
        if (voteRepository.findVoteByDateVoteAndUser(LocalDate.now(), user).isEmpty()) {
            return voteRepository.save(vote);
        } else {
            throw new IllegalRequestDataException("The vote has already been counted by today");
        }
    }

    @Transactional
    public void update(LocalTime localTime, int restaurantId, User user) {
        Optional<Vote> voteOptional = voteRepository.findVoteByDateVoteAndUser(LocalDate.now(), user);
        if (voteOptional.isEmpty()) {
            throw new UpdateConflict("Create a voice first");
        } else if (localTime.isAfter(DEADLINE_TIME_VOTE)) {
            throw new UpdateConflict("The vote has already been counted, after 11:00 you cannot change" +
                    " the voting result");
        }
        Vote vote = voteOptional.get();
        vote.setRestaurant(checkNotFoundOptional(restaurantRepository.findById(restaurantId),
                "restaurantId = " + restaurantId));
        voteRepository.save(vote);
    }

    public List<Vote> getAllByUser(User user) {
        return voteRepository.findAllByUser(user);
    }

    public Vote getVoteByDateVoteAndUser(LocalDate dateVote, User user) {
        return checkNotFoundOptional(voteRepository.findVoteByDateVoteAndUser(dateVote, user),
                "dateVote = " + dateVote + " user = " + user);
    }
}
