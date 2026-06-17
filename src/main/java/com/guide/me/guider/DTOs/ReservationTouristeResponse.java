package com.guide.me.guider.DTOs;

import com.guide.me.guider.entities.StatutReserv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor @AllArgsConstructor @Data
public class ReservationTouristeResponse {

	private Long id;
	private Long guideId;
    private String guideNom;
    private List<LocalDate> dates;
    private StatutReserv statut;
    
}
