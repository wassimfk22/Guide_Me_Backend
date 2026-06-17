package com.guide.me.guider.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Inheritance(strategy = InheritanceType.JOINED)
@Entity @NoArgsConstructor @AllArgsConstructor @Data
public abstract class Utilisateur {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String username;
    
    @Column
    private int age;

    @Column(unique = true)
    private String email;

    @Column
    private String password;
    
    @Column
    private String telephone;

    @Enumerated(EnumType.STRING)
    private RoleUser role;
    
}