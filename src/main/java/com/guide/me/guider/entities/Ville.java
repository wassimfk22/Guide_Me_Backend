package com.guide.me.guider.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity @NoArgsConstructor @AllArgsConstructor @Data
public class Ville {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String nom;

    @ManyToOne
    private Pays pays;

    @OneToMany(mappedBy = "ville")
    private List<Guide> guides;
}
