package ru.dlukin.restaurant_voting.web.user;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.to.UserTo;
import ru.dlukin.restaurant_voting.web.AuthUser;
import ru.dlukin.restaurant_voting.web.abstractcontroller.AbstractUserController;

import java.net.URI;

@RestController
@RequestMapping(value = UserAccountController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UserAccountController extends AbstractUserController {

    static final String REST_URL = "/api/user/account";

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    public User get(@AuthenticationPrincipal AuthUser authUser) {
        return super.get(authUser.id());
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @CacheEvict(value = "users", key = "#authUser.username")
    public void delete(@AuthenticationPrincipal AuthUser authUser) {
        super.delete(authUser.id());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> registration(@RequestBody UserTo userTo) {
        User created = super.create(userTo);
        URI uriOfNewResource =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL + "/{id}")
                        .buildAndExpand(created.getId())
                        .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody User user, @AuthenticationPrincipal AuthUser authUser) {
        User oldUser = authUser.getUser();
        user.setRoles(oldUser.getRoles());
        if (user.getPassword() == null) {
            user.setPassword(oldUser.getPassword());
        }
        super.update(user, authUser.id());
    }
}