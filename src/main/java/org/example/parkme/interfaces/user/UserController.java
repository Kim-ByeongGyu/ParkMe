package org.example.parkme.interfaces.user;

import lombok.RequiredArgsConstructor;
import org.example.parkme.application.user.UserService;
import org.example.parkme.domain.user.User;
import org.example.parkme.interfaces.user.dto.LoginRequest;
import org.example.parkme.interfaces.user.dto.UserRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserRequest request) {
        userService.register(request);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        String token = userService.login(request);
        return ResponseEntity.ok().body(token);
    }

}
