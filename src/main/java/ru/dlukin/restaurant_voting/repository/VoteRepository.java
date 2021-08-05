package ru.dlukin.restaurant_voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.model.Vote;

import java.util.Date;
import java.util.List;

@Repository
@Transactional(readOnly = true)
public interface VoteRepository extends BaseRepository<Vote> {
    List<Vote> findAllByDateVote(Date dateVote);

    List<Vote> findAllByRestaurant(Restaurant restaurant);

    List<Vote> findAllByUser(User user);

    List<Vote> findAllByRestaurantAndDateVote(Restaurant restaurant, Date dateVote);
}
