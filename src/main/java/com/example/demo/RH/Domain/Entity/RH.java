package com.example.demo.RH.Domain.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Table(name = "rh")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RH {

    @Id
    @GeneratedValue
    @UuidGenerator
    private UUID id;

    @Column(nullable = false)
    private String date;

    @Column(nullable = false)
    private String type;
}
