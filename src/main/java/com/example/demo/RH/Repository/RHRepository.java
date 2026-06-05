package com.example.demo.RH.Repository;

import com.example.demo.RH.Domain.Entity.RH;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface RHRepository extends JpaRepository<RH, UUID> {
    boolean existsByDate(String date);
    Optional<RH> findByDate(String date);

}
