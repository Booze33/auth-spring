package com.booze.authDemo.repo;

import com.booze.authDemo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepo extends JpaRepository<User, UUID> {
    User findByEmail(String email);
}
