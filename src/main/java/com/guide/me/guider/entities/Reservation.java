package com.guide.me.guider.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity @NoArgsConstructor @AllArgsConstructor @Data
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(
    	    mappedBy = "reservation",
    	    cascade = CascadeType.ALL,
    	    orphanRemoval = true
    	)
    private List<ReservationDate> dates;

    @Enumerated(EnumType.STRING)
    private StatutReserv statut;

    @ManyToOne
    private Guide guide;

    @ManyToOne
    private Visiteur visiteur;

    @OneToOne(mappedBy = "reservation")
    private Avis avis;
}
