package com.guide.me.guider.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
public class GuideResponse {
	
	private Long id;
    private String nom;
    private String langues;
    private String ville;
    private double prixDemiJournee;
    private double prixJournee;
    private double noteMoyenne;
    private Long paysId;

}
