package com.guide.me.guider.DTOs;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateGuideProfileRequest {
    
    @NotBlank(message = "La description est obligatoire")
    @Size(min = 5, max = 90, message = "La description doit contenir au moins 5 caractères")
    private String description;
    
    @NotBlank(message = "Les langues sont obligatoires")
    private String langues;
    
    @Min(value = 0, message = "Le prix doit être positif")
    private double prixDemiJournee;
    
    @Min(value = 0, message = "Le prix doit être positif")
    private double prixJournee;
    
}
