package ru.dlukin.restaurant_voting.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dlukin.restaurant_voting.model.Vote;
import ru.dlukin.restaurant_voting.service.VoteService;
import ru.dlukin.restaurant_voting.web.AuthUser;

import javax.validation.Valid;
import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteController {

    public static final String REST_URL = "/api/user/votes";

    @Autowired
    private VoteService voteService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> create(@Valid @RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        Vote created = voteService.create(restaurantId, authUser.getUser());
        URI uriOfNewResource =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL + "/{id}")
                        .buildAndExpand(created.getId())
                        .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping
    public Vote update(@Valid @RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser){
        return voteService.create(restaurantId, authUser.getUser());
    }

    @GetMapping(value = "/by-restaurant-today")
    @ResponseStatus(value = HttpStatus.OK)
    public List<Vote> getAllByRestaurantAndDateVoteToday(@RequestParam int restaurantId,
                                                    @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate dateVote) {
        return voteService.getAllByRestaurantAndDateVote(restaurantId, LocalDate.now());
    }
}