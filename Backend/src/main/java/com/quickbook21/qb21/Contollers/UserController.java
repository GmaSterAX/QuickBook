package com.quickbook21.qb21.Contollers;

import com.quickbook21.qb21.Repositories.UserRepository;
import com.quickbook21.qb21.models.Users;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/qb")
public class UserController {
    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/users")
    public ResponseEntity<List<Users>> getUser(){
        List<Users> users = userRepository.findAll();
        return ResponseEntity.ok(users);
    }

}
