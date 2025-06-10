package com.softwarearchitecture.QuickBook.Security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.header.writers.StaticHeadersWriter; // Bu import'u ekle

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF korumasını devre dışı bırakır. Dikkatli kullanın!
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/**").permitAll() // Tüm isteklere şimdilik izin verir. Kendi yollarınızı buraya ekleyin.
                        .anyRequest().authenticated() // Diğer tüm istekler kimlik doğrulaması gerektirir
                )
                .sessionManagement(sess -> sess
                        .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Oturum yönetimi
                )
                .formLogin(form -> form.disable()) // Varsayılan form tabanlı girişi devre dışı bırakır
                .headers(headers -> headers // GÜVENLİK BAŞLIKLARI BURADA YAPILANDIRILIR
                        .frameOptions(frameOptions -> frameOptions.deny()) // X-Frame-Options: DENY - Tıklama korsanlığını önler
                        .contentTypeOptions(contentTypeOptions -> contentTypeOptions.disable()) // X-Content-Type-Options: nosniff - MIME türü koklamayı önler
                        .addHeaderWriter(new StaticHeadersWriter("Referrer-Policy", "strict-origin-when-cross-origin")) // Referrer-Policy başlığını ekler
                        .xssProtection(xss -> xss.headerValue("1; mode=block")) // X-XSS-Protection - XSS saldırılarına karşı koruma sağlar
                        .httpStrictTransportSecurity(hsts -> hsts.includeSubDomains(true).maxAgeInSeconds(31536000)) // HSTS - Tarayıcıların sitenize sadece HTTPS ile erişmesini zorlar
                );

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(){
           return new CustomUserDetailsService();
        throw new UnsupportedOperationException("CustomUserDetailsService bean'i yapılandırılmalıdır.");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(); // Şifreleri güvenli bir şekilde hashlemek için BCrypt kullanır
    }

    @Bean
    public AuthenticationManager authenticationManager
            (UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(daoAuthenticationProvider); // Kimlik doğrulama yöneticisi
    }
