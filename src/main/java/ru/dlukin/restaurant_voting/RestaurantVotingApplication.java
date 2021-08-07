package ru.dlukin.restaurant_voting;

import lombok.AllArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ru.dlukin.restaurant_voting.repository.DishRepository;
import ru.dlukin.restaurant_voting.repository.RestaurantRepository;
import ru.dlukin.restaurant_voting.repository.UserRepository;
import ru.dlukin.restaurant_voting.repository.VoteRepository;
import ru.dlukin.restaurant_voting.service.UserService;

@SpringBootApplication
@AllArgsConstructor
public class RestaurantVotingApplication implements ApplicationRunner {
    private final UserService userService;

    public static void main(String[] args) {
        SpringApplication.run(RestaurantVotingApplication.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        userService.delete(100);
      //  System.out.println(userService.getAll());
    }
}
