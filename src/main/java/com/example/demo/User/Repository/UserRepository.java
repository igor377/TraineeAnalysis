package com.example.demo.User.Repository;

import com.example.demo.User.Domain.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID>  {
    java.util.Optional<User> findByEmail(String email);
}
