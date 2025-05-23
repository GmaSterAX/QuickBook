package com.softwarearchitecture.QuickBook.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;
import com.softwarearchitecture.QuickBook.Repository.UserRepository;

import java.util.ArrayList;

@Service
public class CustomUserDetailService implements UserDetailsService{

    private final UserRepository userRepository;

    @Autowired
    public CustomUserDetailService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        return userRepository.findByMail(mail)
                .map(user -> new org.springframework.security.core.userdetails.User(
                        user.getMail(), user.getPassword(), new ArrayList<>()))
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
