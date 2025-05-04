package com.quickbook21.qb21.Contollers;

import com.quickbook21.qb21.Dto.RegisterDto;
import com.quickbook21.qb21.Repositories.UserRepository;
import com.quickbook21.qb21.models.Users;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private AuthenticationManager authenticationManager;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto){
        if(userRepository.existsByNameOrMailOrPhone(registerDto.getName(),registerDto.getMail(),registerDto.getPhone())){
            return new ResponseEntity<>("User is already exist.", HttpStatus.BAD_REQUEST);
        }else{
            Users user = new Users();
            user.setName(registerDto.getName());
            user.setMail(registerDto.getMail());
            user.setPhone(registerDto.getPhone());
            user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

            userRepository.save(user);
        }
        return ResponseEntity.ok("Account is created successfully!");
    }
}
