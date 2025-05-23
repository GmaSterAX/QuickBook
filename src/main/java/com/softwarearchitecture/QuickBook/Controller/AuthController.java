package com.softwarearchitecture.QuickBook.Controller;


import com.softwarearchitecture.QuickBook.Dto.LoginRequestDto;
import com.softwarearchitecture.QuickBook.Dto.RegisterDto;
import com.softwarearchitecture.QuickBook.Model.User;
import com.softwarearchitecture.QuickBook.Repository.UserRepository;
import com.softwarearchitecture.QuickBook.Security.CustomUserDetailService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
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

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          CustomUserDetailService customUserDetailsService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Login Endpoint
    @PostMapping("/login")
    public String login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request, Model model) {
        try {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(
                            loginRequestDto.getMail(), loginRequestDto.getPassword());

            authenticationManager.authenticate(authToken);

            User user = userRepository.findByMail(loginRequestDto.getMail())
                .orElseThrow(() -> new UsernameNotFoundException("User couldn't found"));

            if (user.getName() == null || user.getName().isBlank()) {
                return "";
            }
            String[] words = user.getName().trim().split("\\s+");
            StringBuilder userInitials = new StringBuilder();

            for (String word : words) {
                if (!word.isEmpty()) {
                    userInitials.append(Character.toUpperCase(word.charAt(0)));
                }
            }
            request.getSession().setAttribute("userInitials", userInitials);
        return "/";
        } catch (Exception e) {
            e.printStackTrace(); // Daha fazla detay için
            model.addAttribute("error", "Login wasn't successfull: " + e.getMessage());
        return "login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate(); // tüm session'ı siler
        return "login";
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