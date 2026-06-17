package com.guide.me.guider.DTOs;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DisponibiliteGuideRequest {
    
    @NotNull(message = "La date est obligatoire")
    private LocalDate date;
    
}