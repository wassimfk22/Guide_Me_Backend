package com.guide.me.guider.entities;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity @NoArgsConstructor @AllArgsConstructor @Getter @Setter
@OnDelete(action = OnDeleteAction.CASCADE)
public class Guide extends Utilisateur {

	@Column
    private String description;
	
	@Column
    private String langues;

    @ManyToOne @JsonIgnore
    private Ville ville;

    @Column
    private double prixDemiJournee;
    
    @Column
    private double prixJournee;
    
    @Column
    private double noteMoyenne;

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Disponibilite> disponibilites;

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Reservation> reservations;

    @OneToMany(mappedBy = "guide", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avis> avis;
    
}
