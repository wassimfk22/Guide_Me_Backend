package com.guide.me.guider.Controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.guide.me.guider.DTOs.GuideAvisResponse;
import com.guide.me.guider.DTOs.GuideResponse;
import com.guide.me.guider.DTOs.UpdateGuideProfileRequest;
import com.guide.me.guider.Security.AuthUtil;
import com.guide.me.guider.Services.GuideService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping ("/api/guides")
@RequiredArgsConstructor
@Validated
public class GuideController {
	
	private final GuideService guideService;
	private final AuthUtil authUtil;

	@GetMapping ("/touriste/pays/{paysId}")
	public List<GuideResponse> getGuidesByPays( @PathVariable Long paysId) {
		return this.guideService.getGuidesByPays(paysId);
	}
	
	@GetMapping("/touriste/pays/{paysId}/ville")
	public List<GuideResponse> getGuidesByPaysAndVilleNom(@PathVariable Long paysId,
	                                              @RequestParam String nom) {
	    return guideService.getGuidesByPaysAndVilleNom(paysId, nom);
	}
	
	/**
	 * VOIR AVIS d'un guide (PUBLIC - pour touristes)
	 * GET /api/guides/{id}/avis
	 */
	@GetMapping("/touriste/avis/{id}")
	public ResponseEntity<List<GuideAvisResponse>> getAvisPublic(@PathVariable Long id) {
		return guideService.getAvisPublic(id);
	}
	
	/**
     * VOIR PROFIL PUBLIC d'un guide (PUBLIC - pour touristes)
     * GET /api/guides/{id}
     */
    @GetMapping("/touriste/{id}")
    public ResponseEntity<?> getProfilPublic(@PathVariable Long id) {
        return guideService.getProfilPublic(id);
    }
	
	/**
     * VOIR MON PROFIL (Guide connecté)
     * GET /api/guides/me
     */
    @GetMapping("/me")
    public ResponseEntity<?> getMonProfil(Authentication auth) {
        Long guideId = authUtil.getUserIdFromAuth(auth);
        return guideService.getMonProfil(guideId);
    }
    
    /**
     * MODIFIER MON PROFIL (Guide connecté)
     * PUT /api/guides/me
     */
    @PutMapping("/me")
    public ResponseEntity<?> modifierMonProfil(
            @RequestBody @Valid UpdateGuideProfileRequest request,
            Authentication auth) {
        
        Long guideId = authUtil.getUserIdFromAuth(auth);
        return guideService.modifierMonProfil(guideId, request);
    }
    
    /**
     * VOIR MES AVIS (Guide connecté)
     * GET /api/guides/me/avis
     */
    @GetMapping("/me/avis")
    public ResponseEntity<List<GuideAvisResponse>> getMesAvis(Authentication auth) {
        Long guideId = authUtil.getUserIdFromAuth(auth);
        return guideService.getMesAvis(guideId);
    }
    
    
    
}
