package com.guide.me.guider.DTOs;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterTouristeRequest {
    
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
    
    @NotNull(message = "Le pays d'origine est obligatoire")
    private Long paysOrigineId;
}