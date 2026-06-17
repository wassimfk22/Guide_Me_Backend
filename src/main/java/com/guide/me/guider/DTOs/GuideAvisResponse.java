package com.guide.me.guider.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideAvisResponse {
    
    private Long avisId;
    private String touristeUsername;
    private int note;
    private String commentaire;
    private LocalDate date;
    
}
