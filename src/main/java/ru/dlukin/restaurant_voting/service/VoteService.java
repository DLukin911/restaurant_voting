package ru.dlukin.restaurant_voting.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.model.Vote;
import ru.dlukin.restaurant_voting.repository.VoteRepository;
import ru.dlukin.restaurant_voting.util.exception.IllegalRequestDataException;

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

    @Transactional
    public Vote create(Restaurant restaurant, User user) {
        Vote vote = new Vote(null, restaurant, user);
        if (voteRepository.findVoteByDateVoteAndUser(LocalDate.now(), user).isEmpty()) {
            return voteRepository.save(vote);
        } else {
            throw new IllegalRequestDataException("The vote has already been counted by today");
        }
    }

    @Transactional
    public void update(User user) {
        Optional<Vote> vote = voteRepository.findVoteByDateVoteAndUser(LocalDate.now(), user);
        if (vote.isEmpty()) {
            throw new IllegalRequestDataException("Create a voice first");
        } else if (LocalTime.now().isAfter(DEADLINE_TIME_VOTE)) {
            throw new IllegalArgumentException("The vote has already been counted, after 11:00 you cannot change" +
                    " the voting result");
        }
        voteRepository.save(vote.get());
    }

    public List<Vote> getAllByUser(User user) {
        return voteRepository.findAllByUser(user);
    }

    public Vote getVoteByDateVoteAndUser(LocalDate dateVote, User user) {
        return checkNotFoundOptional(voteRepository.findVoteByDateVoteAndUser(dateVote, user),
                "dateVote = " + dateVote + " user = " + user);
    }
}
