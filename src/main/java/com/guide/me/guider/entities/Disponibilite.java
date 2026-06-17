package com.guide.me.guider.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity @NoArgsConstructor @AllArgsConstructor @Data
@Table(
	    name = "disponibilite",
	    uniqueConstraints = {
	        @UniqueConstraint(columnNames = {"guide_id", "date"})
	    }
)
public class Disponibilite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDate date;

    @Enumerated(EnumType.STRING)
    private StatutDispo statut;

    @ManyToOne
    @JsonIgnore
    private Guide guide;
    
}
