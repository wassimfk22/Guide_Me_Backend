package com.guide.me.guider.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guide.me.guider.DTOs.DeleteDatesRequest;
import com.guide.me.guider.DTOs.DisponibiliteGuideRequest;
import com.guide.me.guider.DTOs.DisponibiliteResponse;
import com.guide.me.guider.Security.AuthUtil;

import java.util.List;

import com.guide.me.guider.Services.DisponibiliteService;
import com.guide.me.guider.entities.Disponibilite;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping ("/api/disponibilites")
@RequiredArgsConstructor
public class DisponibiliteController {
	
	private final DisponibiliteService dispoService;
    private final AuthUtil authUtil;
	
	@GetMapping("/{guideId}/calendar")
    public List<DisponibiliteResponse> getGuideCalendar( 
    		@PathVariable Long guideId) {
		
        return dispoService.getGuideCalendar(guideId);
    }
	
	@GetMapping("/guide/{guideId}")
    public ResponseEntity<List<Disponibilite>> getDisponibilitesLibresGuide(
            @PathVariable Long guideId) {
        
        return dispoService.getMesDisponibilitesLibres(guideId);
    }
	
    @PostMapping
    public ResponseEntity<String> creerDisponibilite(
            @RequestBody @Valid DisponibiliteGuideRequest request,
            Authentication auth) {
        
        Long guideId = authUtil.getUserIdFromAuth(auth);
        return dispoService.creerDisponibilite(guideId, request);
    }
    
    @GetMapping("/me")
    public ResponseEntity<List<Disponibilite>> getMesDisponibilites(
            Authentication auth) {
        
        Long guideId = authUtil.getUserIdFromAuth(auth);
        List<Disponibilite> dispos = dispoService.getMesDisponibilites(guideId);
        return ResponseEntity.ok(dispos);
    }
    
    @GetMapping("/me/libres")
    public ResponseEntity<List<Disponibilite>> getMesDisponibilitesLibres(
            Authentication auth) {
        
        Long guideId = authUtil.getUserIdFromAuth(auth);
        return dispoService.getMesDisponibilitesLibres(guideId);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerDisponibilite(
            @PathVariable Long id,
            Authentication auth) {
        
        Long guideId = authUtil.getUserIdFromAuth(auth);
        return dispoService.supprimerDisponibilite(id, guideId);
    }
    
    @DeleteMapping("/multi-dispo")
    public ResponseEntity<String> supprimerPlusieursDisponibilites(
            @RequestBody  DeleteDatesRequest request,
            Authentication auth) {
        
        Long guideId = authUtil.getUserIdFromAuth(auth);
        return dispoService.supprimerPlusieursDisponibilites(request.getIds(), guideId);
    }
    
    
	
	

}
