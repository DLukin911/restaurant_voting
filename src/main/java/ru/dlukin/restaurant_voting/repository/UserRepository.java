package ru.dlukin.restaurant_voting.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dlukin.restaurant_voting.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
