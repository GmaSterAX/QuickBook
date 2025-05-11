package com.softwarearchitecture.QuickBook.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.softwarearchitecture.QuickBook.Model.User;
import com.softwarearchitecture.QuickBook.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
        User user = userRepository.findByName(name)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getName())
                .password(user.getPassword())  // Veritabanındaki encode edilmiş şifreyi al
                .roles("USER")  // Kullanıcı rolü
                .build();
    }
}
