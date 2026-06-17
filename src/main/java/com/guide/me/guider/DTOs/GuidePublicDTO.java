package com.guide.me.guider.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class GuidePublicDTO {
	
	private Long id;
    private String username;
    private int age;
    private String description;
    private String langues;
    private String ville;
    private String pays;
    private double prixDemiJournee;
    private double prixJournee;
    private double noteMoyenne;
    private int nombreAvis;

}
