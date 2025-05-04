package com.quickbook21.qb21.Contollers;

import com.quickbook21.qb21.Dto.UserDto;
import com.quickbook21.qb21.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    //CREATE User REST API
    @PostMapping("users-create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto savedUser = userService.createUser(userDto);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }


    //GET User REST API
    @GetMapping("users-get_{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") long userId){ //PathVariabledaki id, GetMappingteki id'ye yazılır.
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }


    @GetMapping("users-get_all")
    public ResponseEntity<List<UserDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    //UPDATE User REST API
    @PutMapping("users-update_{id}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") long userId,
                                              @RequestBody UserDto updatedUser){
        UserDto userDto = userService.updateUser(userId, updatedUser);
        return ResponseEntity.ok(userDto);
    }
    @DeleteMapping("users-delete_{id}")
    public ResponseEntity<String> deleteUser(@PathVariable("id") long userId){
        userService.deleteUser(userId);
        return ResponseEntity.ok("User deleted successfully!");
    }
}
