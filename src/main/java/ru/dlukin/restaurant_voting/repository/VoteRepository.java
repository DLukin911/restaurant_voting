package ru.dlukin.restaurant_voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.model.Vote;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    List<Vote> findAllByUser(User user);

    Optional<Vote> findVoteByDateVoteAndUser(LocalDate dateVote, User user);
}
