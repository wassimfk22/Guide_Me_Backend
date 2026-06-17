package com.guide.me.guider.DTOs;

import java.time.LocalDate;

import com.guide.me.guider.entities.StatutDispo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
public class DisponibiliteResponse {
	
	private LocalDate date;
    private StatutDispo statut;

}
