package com.guide.me.guider.DTOs;

import com.guide.me.guider.entities.RoleUser;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
	
	private String token;
    private Long userId;
    private String username;
    private RoleUser role;
    private String message;

}
