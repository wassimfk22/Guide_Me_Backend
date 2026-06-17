package com.guide.me.guider.DTOs;

import java.time.LocalDate;
import java.util.List;

import com.guide.me.guider.entities.StatutReserv;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationGuideResponse {
    
    private Long reservationId;
    private String touristeUsername;
    private String touristeTelephone;
    private List<LocalDate> dates;
    private StatutReserv statut;
    private int nombreJours;
}
