package com.softwarearchitecture.QuickBook.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.softwarearchitecture.QuickBook.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String mail)
        throws UsernameNotFoundException {
            return userRepository.findByMail(mail)
                .orElseThrow( ()-> new UsernameNotFoundException
                ("user not found"));
    }

    
}