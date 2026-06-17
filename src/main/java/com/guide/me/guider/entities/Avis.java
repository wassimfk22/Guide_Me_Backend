package com.guide.me.guider.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity @NoArgsConstructor @AllArgsConstructor @Data
public class Avis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column @Min(1) @Max(5)
    private int note;
    
    @Column
    private String commentaire;
    
    @Column
    private LocalDate date;

    @OneToOne ( optional = false )
    private Reservation reservation;

    @ManyToOne ( optional = false )
    private Guide guide;

    @ManyToOne ( optional = false )
    private Visiteur visiteur;
}
