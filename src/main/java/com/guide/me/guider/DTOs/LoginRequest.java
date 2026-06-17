package com.guide.me.guider.DTOs;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor
public class LoginRequest {
	
	@NotBlank (message = "Le username est obligatoire !")
	private String username;
	@NotBlank (message = "Le password est obligatoire !")
    private String password;

}
