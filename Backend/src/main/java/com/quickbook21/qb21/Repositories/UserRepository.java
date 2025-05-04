package com.quickbook21.qb21.Repositories;

import com.quickbook21.qb21.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<Users, Long> {
    Boolean existsByNameOrMailOrPhone(String name, String mail, String phone);
}
