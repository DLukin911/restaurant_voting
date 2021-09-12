package ru.dlukin.restaurant_voting.web.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.dlukin.restaurant_voting.model.Vote;
import ru.dlukin.restaurant_voting.service.VoteService;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = AdminVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminVoteController {

    public static final String REST_URL = "/api/admin/votes";

    @Autowired
    private VoteService voteService;

    @GetMapping
    public List<Vote> getAll() {
        return voteService.getAll();
    }

    @GetMapping(value = "/by-restaurant")
    public List<Vote> getAllByRestaurant(@RequestParam int restaurantId) {
        return voteService.getAllByRestaurant(restaurantId);
    }

    @GetMapping(value = "/by-restaurant-datevote")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Vote> getAllByRestaurantAndDateVote(@RequestParam int restaurantId,
                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateVote) {
        return voteService.getAllByRestaurantAndDateVote(restaurantId, dateVote);
    }
}