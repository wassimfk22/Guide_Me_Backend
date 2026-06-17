package com.guide.me.guider.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity @NoArgsConstructor @AllArgsConstructor @Data
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String message;
    
    @Column
    private LocalDateTime dateEnvoi;

    @ManyToOne
    private Utilisateur utilisateur;
}
