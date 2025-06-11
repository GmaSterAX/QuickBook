package com.softwarearchitecture.QuickBook.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import com.softwarearchitecture.QuickBook.Dto.LoginRequestDto;
import com.softwarearchitecture.QuickBook.Dto.NotificationDto;
import com.softwarearchitecture.QuickBook.Dto.RegisterDto;
import com.softwarearchitecture.QuickBook.Model.User;
import com.softwarearchitecture.QuickBook.Repository.UserRepository;
import com.softwarearchitecture.QuickBook.Service.EmailService;
import com.softwarearchitecture.QuickBook.Service.NotificationService;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final NotificationService notificationService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager,
                          UserRepository userRepository,
                          PasswordEncoder passwordEncoder,
                          EmailService emailService,
                          NotificationService notificationService) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.notificationService = notificationService;
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

        NotificationDto welcomeNotification = new NotificationDto();

        welcomeNotification.setMessage("Hello and welcome to QuickBook!\n" + //
                        "\n" + //
                        "We’re excited to have you join our community. Now, booking your perfect stay is easier than ever. Explore, discover, and enjoy seamless hotel reservations tailored just for you.\n" + //
                        "\n" + //
                        "If you ever need help, our team is here to support you every step of the way. Thanks for choosing QuickBook — we’re happy to be part of your journey!\n" + //
                        "\n" + //
                        "Happy travels!\n" + //
                        "\n" + //
                        "— The QuickBook Team");
        welcomeNotification.setMessageTitle("Welcome to QuickBook!");
        welcomeNotification.setUser_id(user.getId());

        notificationService.createNotification(welcomeNotification);

        System.out.println("VERIFICATION LINK: https://qquickbook.com/verify-email?token=" + token);

        emailService.sendSimpleEmail(
                user.getMail(),
                "Email Verification",
                "Click on the link to verify your account:\n" +
                        "https://qquickbook.com/verify-email?token=" + token
        );
        return ResponseEntity.ok("Account created successfully! Please verify your email.");
    }

    // ✅ Email doğrulama linki
    @GetMapping("/verify-email")
    public String verifyEmail(@RequestParam("token") String token, Model model) {
        if (token == null || token.isEmpty()) {
            model.addAttribute("message", "Token is required.");
            model.addAttribute("success", false);
            return "email-verification";
        }

        Optional<User> optionalUser = userRepository.findByVerificationToken(token);

        if (optionalUser.isEmpty()) {
            model.addAttribute("message", "Invalid or expired token.");
            model.addAttribute("success", false);
            return "email-verification";
        }

        User user = optionalUser.get();
        user.setEmailVerified(true);
        user.setVerificationToken(null);
        userRepository.save(user);

        model.addAttribute("message", "Email verified successfully!");
        model.addAttribute("success", true);
        return "email-verification";
    }

    @PostMapping("/change-the-password")
    public ResponseEntity<String> changePassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        String newPassword = request.get("newPassword");

        if (email == null || newPassword == null) {
            return ResponseEntity.badRequest().body("Missing Credentials");
        }

        Optional<User> userOptional = userRepository.findByMail(email);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body("User not found");
        }

        User user = userOptional.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        return ResponseEntity.ok("Password changed succesfully");
    }
}
