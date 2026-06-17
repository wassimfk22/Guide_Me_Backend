// AuthController.java
package com.guide.me.guider.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.guide.me.guider.DTOs.*;
import com.guide.me.guider.Services.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    
    private final AuthService authService;
    
    /**
     * LOGIN
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request) {
        return authService.login(request);
    }
    
    /**
     * REGISTER GUIDE
     */
    @PostMapping("/register/guide")
    public ResponseEntity<?> registerGuide(@RequestBody @Valid RegisterGuideRequest request) {
        return authService.registerGuide(request);
    }
    
    /**
     * REGISTER TOURISTE
     */
    @PostMapping("/register/touriste")
    public ResponseEntity<?> registerTouriste(@RequestBody @Valid RegisterTouristeRequest request) {
        return authService.registerTouriste(request);
    }
}