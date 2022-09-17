package dev.pkulik.snap.controller;

import dev.pkulik.snap.service.UserService;
import dev.pkulik.snap.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class UserController {
    private final UserService userService;

    @PostMapping("/auth/register")
    ResponseEntity<User> createUser(@RequestBody User user) {
        Optional<User> optionalUser = userService.createUser(user);

        if (optionalUser.isEmpty())
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        return ResponseEntity.ok(optionalUser.get());
    }
}
