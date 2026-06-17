package com.guide.me.guider.Controllers;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.guide.me.guider.DTOs.ReservationDemandeResponse;
import com.guide.me.guider.DTOs.ReservationGuideResponse;
import com.guide.me.guider.DTOs.ReservationRequest;
import com.guide.me.guider.DTOs.ReservationTouristeResponse;
import com.guide.me.guider.Security.AuthUtil;
import com.guide.me.guider.Services.ReservationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
@Validated
public class ReservationController {
	
	private final ReservationService reservationService;
	private final AuthUtil authUtil;

	@PostMapping ("/tourist/reserver")
	public ResponseEntity<String> reserver(
	        @RequestBody @Valid ReservationRequest request, 
	        Authentication auth) {
		
	    Long visiteurId = authUtil.getUserIdFromAuth(auth);
	    return reservationService.reserverDisponibilite(request, visiteurId);
	}
    
    @GetMapping("/tourist/me")
    public List<ReservationTouristeResponse> getMyReservations(Authentication auth) {
        Long visiteurId = authUtil.getUserIdFromAuth(auth);
        return reservationService.getReservationsByTouriste(visiteurId);
    }
    
    @DeleteMapping("/tourist/{id}")
    public ResponseEntity<?> annulerReservation(@PathVariable Long id, Authentication auth) {
        Long visiteurId = this.authUtil.getUserIdFromAuth(auth);
        reservationService.annulerReservation(id, visiteurId);
        return ResponseEntity.ok("Réservation annulée");
    }
    
    /**
     * VOIR MES DEMANDES EN ATTENTE (Guide)
     * GET /api/reservations/guide/demandes
     */
    @GetMapping("/guide/demandes")
    public ResponseEntity<List<ReservationDemandeResponse>> getMesDemandesEnAttente(
            Authentication auth) {
        
        Long guideId = authUtil.getUserIdFromAuth(auth);
        List<ReservationDemandeResponse> demandes = 
            reservationService.getMesDemandesEnAttente(guideId);
        
        return ResponseEntity.ok(demandes);
    }

    /**
     * VOIR MES RÉSERVATIONS CONFIRMÉES (Guide)
     * GET /api/reservations/guide/confirmees
     */
    @GetMapping("/guide/confirmees")
    public ResponseEntity<List<ReservationGuideResponse>> getMesReservationsConfirmees(
            Authentication auth) {
        
        Long guideId = authUtil.getUserIdFromAuth(auth);
        List<ReservationGuideResponse> confirmees = 
            reservationService.getMesReservationsConfirmees(guideId);
        
        return ResponseEntity.ok(confirmees);
    }

    /**
     * VOIR MON HISTORIQUE COMPLET (Guide)
     * GET /api/reservations/guide/historique
     */
    @GetMapping("/guide/historique")
    public ResponseEntity<List<ReservationDemandeResponse>> getMonHistorique(
            Authentication auth) {
        
        Long guideId = authUtil.getUserIdFromAuth(auth);
        List<ReservationDemandeResponse> historique = 
            reservationService.getMonHistorique(guideId);
        
        return ResponseEntity.ok(historique);
    }

    /**
     * ACCEPTER UNE RÉSERVATION (Guide)
     * PUT /api/reservations/{id}/accepter
     */
    @PutMapping("/guide/accepter/{id}")
    public ResponseEntity<String> accepterReservation(
            @PathVariable Long id,
            Authentication auth) {
        
        Long guideId = authUtil.getUserIdFromAuth(auth);
        return reservationService.accepterReservation(id, guideId);
    }

    /**
     * REFUSER UNE RÉSERVATION (Guide)
     * PUT /api/reservations/{id}/refuser
     */
    @PutMapping("/guide/refuser/{id}")
    public ResponseEntity<String> refuserReservation(
            @PathVariable Long id,
            Authentication auth) {
        
        Long guideId = authUtil.getUserIdFromAuth(auth);
        return reservationService.refuserReservation(id, guideId);
    }
    
    

    

    

}
