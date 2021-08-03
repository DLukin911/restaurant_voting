package ru.dlukin.restaurant_voting;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import ru.dlukin.restaurant_voting.model.Dish;
import ru.dlukin.restaurant_voting.model.Restaurant;
import ru.dlukin.restaurant_voting.model.Role;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.repository.DishRepository;
import ru.dlukin.restaurant_voting.repository.RestaurantRepository;
import ru.dlukin.restaurant_voting.repository.UserRepository;
import ru.dlukin.restaurant_voting.repository.VoteRepository;

import java.util.Set;

@SpringBootApplication
@AllArgsConstructor
public class RestaurantVotingApplication implements ApplicationRunner {
    private final UserRepository userRepository;
    private final DishRepository dishRepository;
    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;

    public static void main(String[] args) {
        SpringApplication.run(RestaurantVotingApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userRepository.save(new User("user3@gmail.com", "User3_Name", "User3_LastName", "password", null));
        System.out.println(userRepository.findAll());
    }
}
