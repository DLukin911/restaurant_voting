package ru.dlukin.restaurant_voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.model.Vote;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    List<Vote> findAllByDateTimeVoteBetweenAndUser(LocalDateTime startDate, LocalDateTime endDate, User user);

    List<Vote> findAllByRestaurantId(int restaurantId);

    List<Vote> findAllByRestaurantIdAndDateTimeVoteBetween(int restaurantId, LocalDateTime startDate,
                                                     LocalDateTime endDate);
}
