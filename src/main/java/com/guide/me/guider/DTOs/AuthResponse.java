package com.guide.me.guider.DTOs;

import com.guide.me.guider.entities.RoleUser;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Data
public class AuthResponse {
	
	@NotBlank
	private String token;
	@NotBlank
    private Long userId;
	@NotBlank
    private String username;
	@NotBlank
    private RoleUser role;
	@NotBlank
    private String message;

}
