package ru.dlukin.restaurant_voting.web.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dlukin.restaurant_voting.model.Vote;
import ru.dlukin.restaurant_voting.service.RestaurantService;
import ru.dlukin.restaurant_voting.service.VoteService;
import ru.dlukin.restaurant_voting.web.AuthUser;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = UserVoteController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserVoteController {

    public static final String REST_URL = "/api/user/votes";

    @Autowired
    private VoteService voteService;

    @Autowired
    private RestaurantService restaurantService;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Vote> createVote(@RequestParam int restaurantId, @AuthenticationPrincipal AuthUser authUser) {
        log.info("create with restaurantId {}", restaurantId);
        Vote created = voteService.create(restaurantService.get(restaurantId), authUser.getUser());
        URI uriOfNewResource =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL + "/today-vote")
                        .build()
                        .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@AuthenticationPrincipal AuthUser authUser){
        log.info("update");
        voteService.update(authUser.getUser());
    }

    @GetMapping
    public List<Vote> getAllByUser(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all by User");
        return voteService.getAllByUser(authUser.getUser());
    }

    @GetMapping(value = "/today-vote")
    public Vote getAllByUserForToday(@AuthenticationPrincipal AuthUser authUser) {
        log.info("get all by User for Today");
        return voteService.getVoteByDateVoteAndUser(LocalDate.now(), authUser.getUser());
    }
}