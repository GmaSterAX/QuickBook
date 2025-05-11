package com.softwarearchitecture.QuickBook.Controller;

import  com.softwarearchitecture.QuickBook.Dto.LoginDto;
import  com.softwarearchitecture.QuickBook.Dto.RegisterDto;
import  com.softwarearchitecture.QuickBook.Repository.UserRepository;
import  com.softwarearchitecture.QuickBook.Model.User;
import com.softwarearchitecture.QuickBook.Security.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          CustomUserDetailsService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
    }

    // Login Endpoint
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.getName(), loginDto.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            return new ResponseEntity<>("User signed in successfully!", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Login failed: " + e.getMessage(), HttpStatus.UNAUTHORIZED);
        }
    }

    // Register Endpoint
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByNameOrMailOrPhone(registerDto.getName(), registerDto.getMail(), registerDto.getPhone())) {
            return new ResponseEntity<>("User already exists.", HttpStatus.BAD_REQUEST);
        }

        // Kullanıcıyı oluştur
        User user = new User();
        user.setName(registerDto.getName());
        user.setMail(registerDto.getMail());
        user.setPhone(registerDto.getPhone());

        // Şifreyi encode et
        user.setPassword(passwordEncoder.encode(registerDto.getPassword())); // Şifreyi bcrypt ile encode ediyoruz

        // Veritabanına kaydet
        userRepository.save(user);

        return new ResponseEntity<>("Account created successfully!", HttpStatus.CREATED);
    }
}
