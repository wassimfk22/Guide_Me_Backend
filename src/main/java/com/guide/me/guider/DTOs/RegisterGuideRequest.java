package com.guide.me.guider.DTOs;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class RegisterGuideRequest {
	
	@NotBlank(message = "Le username est obligatoire")
    private String username;
    
    @Min(value = 18, message = "Vous devez avoir au moins 18 ans")
    @Max(value = 100, message = "Âge invalide")
    private int age;
    
    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Email invalide")
    private String email;
    
    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 3, message = "Le mot de passe doit contenir au moins 3 caractères")
    private String password;
    
    @NotBlank(message = "Le téléphone est obligatoire")
    private String telephone;
    
    @NotBlank(message = "La description est obligatoire")
    @Size(min = 20, message = "La description doit contenir au moins 20 caractères")
    private String description;
    
    @NotBlank(message = "Les langues sont obligatoires")
    private String langues;
    
    @NotNull(message = "La ville est obligatoire")
    private Long villeId;
    
    @Min(value = 0, message = "Le prix doit être positif")
    private double prixDemiJournee;
    
    @Min(value = 0, message = "Le prix doit être positif")
    private double prixJournee;

}
