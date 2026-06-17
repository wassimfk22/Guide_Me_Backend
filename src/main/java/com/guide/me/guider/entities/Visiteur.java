package com.guide.me.guider.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity @NoArgsConstructor @AllArgsConstructor @Data
public class Visiteur extends Utilisateur {

    @ManyToOne
    private Pays paysOrigine;

    @OneToMany(mappedBy = "visiteur")
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "visiteur")
    private List<Avis> avis;
    
}
