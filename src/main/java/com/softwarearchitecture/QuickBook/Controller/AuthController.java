package com.softwarearchitecture.QuickBook.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import com.softwarearchitecture.QuickBook.Dto.LoginRequestDto;
import com.softwarearchitecture.QuickBook.Dto.RegisterDto;
import com.softwarearchitecture.QuickBook.Model.User;
import com.softwarearchitecture.QuickBook.Repository.UserRepository;
import com.softwarearchitecture.QuickBook.Service.EmailService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*; 



import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          EmailService emailService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto loginRequestDto, HttpServletRequest request) {
        try {
            System.out.println("Login attempt: " + loginRequestDto.getMail());

            Optional<User> optionalUser = userRepository.findByMail(loginRequestDto.getMail());

            if (optionalUser.isEmpty()) {
                System.out.println("User not found");
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password.");
            }

            User user = optionalUser.get();

            if (!user.isEmailVerified()) {
                return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Please verify your email before logging in.");
            }

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginRequestDto.getMail(), loginRequestDto.getPassword());

            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            HttpSession session = request.getSession(true);
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());

            // Kullanıcı baş harfleri
            String[] words = user.getName().trim().split("\\s+");
            StringBuilder initials = new StringBuilder();
            for (String word : words) {
                if (!word.isEmpty()) {
                    initials.append(Character.toUpperCase(word.charAt(0)));
                }
            }
            session.setAttribute("userInitials", initials.toString());
            session.setAttribute("userMail", user.getMail());

            // Başarılı login sonrası yönlendirme
            return ResponseEntity.status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, "/index")
                    .build();

        } catch (Exception e) {
            System.out.println("Authentication failed: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }



    // ✅ Register - Formdan veri alınmıyorsa JSON'dan
    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<String> register(@RequestBody RegisterDto registerDto) {
        if (userRepository.existsByNameOrMailOrPhone(registerDto.getName(), registerDto.getMail(), registerDto.getPhone())) {
            return ResponseEntity.badRequest().body("User already exists.");
        }

        User user = new User();
        user.setName(registerDto.getName());
        user.setMail(registerDto.getMail());
        user.setPhone(registerDto.getPhone());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));

        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setEmailVerified(false);
        userRepository.save(user);

        System.out.println("VERIFICATION LINK: http://localhost:8080/verify-email?token=" + token);

        emailService.sendSimpleEmail(
                user.getMail(),
                "Email Verification",
                "Click on the link to verify your account:\n" +
                        "http://localhost:8080/verify-email?token=" + token
        );

        return ResponseEntity.ok("Account created successfully! Please verify your email.");
    }

    // ✅ Email doğrulama linki
    @GetMapping("/verify-email")
    @ResponseBody
    public ResponseEntity<String> verifyEmail(@RequestParam("token") String token) {
        if (token == null || token.isEmpty()) {
            return ResponseEntity.badRequest().body("Token is required.");
        }

        Optional<User> optionalUser = userRepository.findByVerificationToken(token);

        if (optionalUser.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid or expired token.");
        }

        User user = optionalUser.get();
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);

        return ResponseEntity.ok("Email verified successfully!");
    }
}
