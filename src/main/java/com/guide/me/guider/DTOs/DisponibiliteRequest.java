package com.guide.me.guider.DTOs;

import java.time.LocalDate;

import com.guide.me.guider.entities.Guide;
import com.guide.me.guider.entities.StatutDispo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
public class DisponibiliteRequest {
	
	@NotBlank
	private LocalDate date;
	
	@NotBlank
	private StatutDispo statut;
	
	@NotBlank
    private Guide guide;
	
	// Je teste automatiquementtt

}
