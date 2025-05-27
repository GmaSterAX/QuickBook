package com.softwarearchitecture.QuickBook.Repository;

import com.softwarearchitecture.QuickBook.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    
    Optional<User> findByName(String name);
    boolean existsByNameOrMailOrPhone(String name, String mail, String phone);
    Optional<User> findByMail(String mail);
    Optional<User> findByVerificationToken(String token);
}
