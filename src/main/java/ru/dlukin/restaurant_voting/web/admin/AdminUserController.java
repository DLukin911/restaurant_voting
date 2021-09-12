package ru.dlukin.restaurant_voting.web.admin;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dlukin.restaurant_voting.model.User;
import ru.dlukin.restaurant_voting.to.UserTo;
import ru.dlukin.restaurant_voting.web.abstractcontroller.AbstractUserController;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = AdminUserController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class AdminUserController extends AbstractUserController {

    static final String REST_URL = "/api/admin/users";

    @Override
    @GetMapping
    public List<User> getAll() {
        return super.getAll();
    }

    @Override
    @GetMapping("/{id}")
    public User get(@PathVariable int id) {
        return super.get(id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> createUser(@Valid @RequestBody UserTo userTo) {
        User created = super.create(userTo);
        URI uriOfNewResource =
                ServletUriComponentsBuilder.fromCurrentContextPath()
                        .path(REST_URL + "/{id}")
                        .buildAndExpand(created.getId())
                        .toUri();
        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@Valid @RequestBody UserTo userTo, @PathVariable int id) {
        super.update(userTo, id);
    }

    @Override
    @GetMapping("/by-mail")
    public User getByMail(@RequestParam String email) {
        return super.getByMail(email);
    }

    @Override
    @GetMapping("/by-name")
    public User getByName(@RequestParam String name) {
        return super.getByName(name);
    }
}