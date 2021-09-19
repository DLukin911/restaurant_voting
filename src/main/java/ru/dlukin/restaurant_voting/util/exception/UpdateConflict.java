package ru.dlukin.restaurant_voting.util.exception;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.http.HttpStatus;

import static org.springframework.boot.web.error.ErrorAttributeOptions.Include.MESSAGE;

public class UpdateConflict extends AppException {
    public UpdateConflict(String msg) {
        super(HttpStatus.CONFLICT, msg, ErrorAttributeOptions.of(MESSAGE));
    }
}