package com.quickbook21.qb21.Contollers;

import com.quickbook21.qb21.Dto.UserDto;
import com.quickbook21.qb21.Repositories.UserRepository;
import com.quickbook21.qb21.Services.UserService;
import com.quickbook21.qb21.models.Users;
import lombok.AllArgsConstructor;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/qb21/")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    //CREATE User REST API
    @PostMapping("create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


    //GET User REST API
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") int userId){ //PathVariabledaki id, GetMappingteki id'ye yazılır.
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }


    @GetMapping("/users-all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());

    }
}
