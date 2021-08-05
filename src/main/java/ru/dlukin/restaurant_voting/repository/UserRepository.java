package ru.dlukin.restaurant_voting.repository;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.User;

import java.util.Optional;

@Repository
@Transactional(readOnly = true)
public interface UserRepository extends BaseRepository<User> {
    Optional<User> findByEmail(String email);

    Optional<User> findByName(String name);
}
