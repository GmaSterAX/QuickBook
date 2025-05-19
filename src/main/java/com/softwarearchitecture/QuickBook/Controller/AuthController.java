package com.softwarearchitecture.QuickBook.Controller;


import com.softwarearchitecture.QuickBook.Dto.LoginRequestDto;
import com.softwarearchitecture.QuickBook.Dto.RegisterDto;
import com.softwarearchitecture.QuickBook.Model.User;
import com.softwarearchitecture.QuickBook.Repository.UserRepository;
import com.softwarearchitecture.QuickBook.Security.CustomUserDetailService;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService customUserDetailsService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          CustomUserDetailService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto loginRequestDto) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getMail(), loginRequestDto.getPassword());

            authenticationManager.authenticate(authToken);

            return ResponseEntity.ok("Login successful!");
        } catch (Exception e) {
            e.printStackTrace(); // Daha fazla detay için
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }

    // Register Endpoint
    @PostMapping("/register")
public ResponseEntity<Map<String, String>> register(@RequestBody RegisterDto registerDto) {
    Map<String, String> response = new HashMap<>();

    if (userRepository.existsByNameOrMailOrPhone(
            registerDto.getName(), 
            registerDto.getMail(), 
            registerDto.getPhone())) {
        
        response.put("message", "This user already exists.");
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    // Kullanıcıyı oluştur
    User user = new User();
    user.setName(registerDto.getName());
    user.setMail(registerDto.getMail());
    user.setPhone(registerDto.getPhone());
    user.setPassword(passwordEncoder.encode(registerDto.getPassword())); // Şifreyi bcrypt ile encode ediyoruz

    // Veritabanına kaydet
    userRepository.save(user);

    response.put("message", "Your account created successfully!");
    return new ResponseEntity<>(response, HttpStatus.CREATED);
}

}