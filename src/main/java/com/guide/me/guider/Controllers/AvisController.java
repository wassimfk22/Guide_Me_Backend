package com.guide.me.guider.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guide.me.guider.DTOs.AvisRequest;
import com.guide.me.guider.Security.AuthUtil;
import com.guide.me.guider.Services.AvisService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@Validated
@RequestMapping ("/api/avis")
@RequiredArgsConstructor
public class AvisController {
	
	private final AvisService avisService;
	private final AuthUtil authUtil;
	
	@PostMapping
	public ResponseEntity<?> noterGuide(
	        @RequestBody @Valid AvisRequest request,
	        Authentication auth) {
	    
	    Long visiteurId = authUtil.getUserIdFromAuth(auth);
	    avisService.noterGuide(request, visiteurId);
	    
	    return ResponseEntity.ok("Merci pour votre avis !");
	}


}
