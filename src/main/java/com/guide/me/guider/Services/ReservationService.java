package com.guide.me.guider.Services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.guide.me.guider.DTOs.ReservationDemandeResponse;
import com.guide.me.guider.DTOs.ReservationGuideResponse;
import com.guide.me.guider.DTOs.ReservationRequest;
import com.guide.me.guider.DTOs.ReservationTouristeResponse;
import com.guide.me.guider.Repositories.DisponibiliteRepository;
import com.guide.me.guider.Repositories.GuideRepository;
import com.guide.me.guider.Repositories.ReservationRepository;
import com.guide.me.guider.Repositories.VisiteurRepository;
import com.guide.me.guider.entities.Disponibilite;
import com.guide.me.guider.entities.Reservation;
import com.guide.me.guider.entities.ReservationDate;
import com.guide.me.guider.entities.StatutDispo;
import com.guide.me.guider.entities.StatutReserv;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {
	
	private final ReservationRepository reservationRepo;
    private final DisponibiliteRepository dispoRepo;
    private final GuideRepository guideRepo;
    private final VisiteurRepository visiteurRepo;
    private final NotificationService notificationService;
    
    /**
     * RESERVER POUR LE TOURISTE
     */
    
    public ResponseEntity <String> reserverDisponibilite ( ReservationRequest request, Long visiteurId ){
    	
    	if (reservationRepo.existsReservationOverlap(
    	        request.getGuideId(),
    	        visiteurId,
    	        request.getDates())) {

    	    return new ResponseEntity<>(
    	        "Vous avez déjà une réservation pour ces dates",
    	        HttpStatus.CONFLICT
    	    );
    	}
    	
    	List <Disponibilite> libres =
    		    dispoRepo.findDisponibilitesLibres(
    		        request.getGuideId(),
    		        request.getDates()
    		    );
    	if (libres.size() != request.getDates().size()) {
    	    return new ResponseEntity <String> 
    	    ("Dates non disponibles", HttpStatus.NOT_ACCEPTABLE);
    	}
    	
    	Reservation r = new Reservation ();
    	r.setGuide(guideRepo.getReferenceById(request.getGuideId()));
    	r.setVisiteur(visiteurRepo.getReferenceById(visiteurId));
    	r.setStatut(StatutReserv.EN_ATTENTE);
    	
    	List<ReservationDate> reservationDates =
    	        request.getDates().stream()
    	        .map(date -> {
    	            ReservationDate rd = new ReservationDate();
    	            rd.setDate(date);
    	            rd.setReservation(r);
    	            return rd;
    	        })
    	        .toList();

    	r.setDates(reservationDates);
    	reservationRepo.save(r);
    	
    	// Notifier le guide
    	notificationService.creerNotification(
    	    request.getGuideId(),
    	    r.getVisiteur().getUsername() + " a demandé une réservation pour " + 
    	    request.getDates().size() + " jour(s)"
    	);
    	
    	return new ResponseEntity <String> 
    	("Resérvation demandée avec succés ! Le guide " 
    	+guideRepo.getReferenceById(request.getGuideId()).getUsername()+ 
    	" pourra voir votre demande. ", HttpStatus.OK);
    	
    }
    
    /**
     * AFFICHER TOUTE LES RESERVATIONS D'UN GUIDE POUR LE TOURISTE
     */

    public List<ReservationTouristeResponse> getReservationsByTouriste(Long visiteurId) {

        List<Reservation> reservations =
                reservationRepo.findByVisiteurIdOrderByIdDesc(visiteurId);

        return reservations.stream()
            .map(r -> new ReservationTouristeResponse(
            	r.getId(),
            	r.getGuide().getId(),
                r.getGuide().getUsername(),
                r.getDates().stream()
                    .map(ReservationDate::getDate)
                    .toList(),
                r.getStatut()
            ))
            .toList();
    }
    
    /**
     * ANNULER UNE RESERVATION POUR LE TOURISTE
     */
    
    public void annulerReservation(Long reservationId, Long visiteurId) {

        Reservation reservation = reservationRepo.findById(reservationId)
            .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        if (!reservation.getVisiteur().getId().equals(visiteurId)) {
            throw new RuntimeException("Action interdite");
        }

        if (reservation.getStatut() != StatutReserv.EN_ATTENTE) {
            throw new RuntimeException(
                "Impossible d'annuler une réservation déjà traitée par le guide"
            );
        }
        reservation.setStatut(StatutReserv.ANNULEE);
        this.reservationRepo.save(reservation);
        
     // Notifier le guide
        notificationService.creerNotification(
            reservation.getGuide().getId(),
            reservation.getVisiteur().getUsername() + 
            " a annulé sa réservation"
        );
    }
    
    /**
     * VOIR LES DEMANDES EN ATTENTE (pour le guide)
     */
    public List<ReservationDemandeResponse> getMesDemandesEnAttente(Long guideId) {
        List<Reservation> demandes = reservationRepo.findDemandesEnAttente(guideId);
        
        return demandes.stream()
            .map(r -> new ReservationDemandeResponse(
                r.getId(),
                r.getVisiteur().getUsername(),
                r.getDates().stream()
                    .map(ReservationDate::getDate)
                    .toList(),
                r.getStatut(),
                r.getDates().size()
            ))
            .toList();
    }

    /**
     * VOIR LES RÉSERVATIONS CONFIRMÉES (pour le guide)
     */
    public List<ReservationGuideResponse> getMesReservationsConfirmees(Long guideId) {
        List<Reservation> confirmees = reservationRepo.findReservationsConfirmees(guideId);
        
        return confirmees.stream()
            .map(r -> new ReservationGuideResponse(
                r.getId(),
                r.getVisiteur().getUsername(),
                r.getVisiteur().getTelephone(),
                r.getDates().stream()
                    .map(ReservationDate::getDate)
                    .toList(),
                r.getStatut(),
                r.getDates().size()
            ))
            .toList();
    }

    /**
     * HISTORIQUE COMPLET (pour le guide)
     */
    public List<ReservationDemandeResponse> getMonHistorique(Long guideId) {
        List<Reservation> historique = reservationRepo.findByGuideIdOrderByIdDesc(guideId);
        
        return historique.stream()
            .map(r -> new ReservationDemandeResponse(
                r.getId(),
                r.getVisiteur().getUsername(),
                r.getDates().stream()
                    .map(ReservationDate::getDate)
                    .toList(),
                r.getStatut(),
                r.getDates().size()
            ))
            .toList();
    }

    /**
     * ACCEPTER UNE RÉSERVATION
     */
    public ResponseEntity<String> accepterReservation(Long reservationId, Long guideId) {
        Reservation reservation = reservationRepo.findById(reservationId)
            .orElseThrow(() -> new RuntimeException("Réservation introuvable"));
        
        if (!reservation.getGuide().getId().equals(guideId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Action interdite");
        }
        
        if (reservation.getStatut() != StatutReserv.EN_ATTENTE) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Cette réservation a déjà été traitée");
        }
        
        reservation.setStatut(StatutReserv.CONFIRMEE);
        reservationRepo.save(reservation);
        
        // Bloquer les disponibilités (LIBRE → RESERVE)
        for (ReservationDate rd : reservation.getDates()) {
            List<Disponibilite> dispos = dispoRepo.findByGuideIdAndDate(
                guideId, 
                rd.getDate()
            );
            
            for (Disponibilite dispo : dispos) {
                dispo.setStatut(StatutDispo.RESERVE);
                dispoRepo.save(dispo);
            }
        }
        
     // Notifier le touriste
        notificationService.creerNotification(
            reservation.getVisiteur().getId(),
            "Votre réservation a été CONFIRMÉE par le guide " +
            reservation.getGuide().getUsername() +
            ". Contact du guide : " +
            reservation.getGuide().getTelephone()
        );
        
     // Notifier le guide
        notificationService.creerNotification(
//        		ID DU RECEPTEUR
            reservation.getGuide().getId(),
//            Message
            "Réservation confirmée ! pour le touriste " + 
            reservation.getVisiteur().getUsername() + 
            " \nRéservation acceptée ! Contact du touriste : " + 
            reservation.getVisiteur().getTelephone()
        );
        
        return ResponseEntity.ok(
            "Réservation acceptée ! Contact du touriste : " + 
            reservation.getVisiteur().getTelephone()
        );
    }

    /**
     * REFUSER UNE RÉSERVATION
     */
    public ResponseEntity<String> refuserReservation(Long reservationId, Long guideId) {
        Reservation reservation = reservationRepo.findById(reservationId)
            .orElseThrow(() -> new RuntimeException("Réservation introuvable"));
        
        if (!reservation.getGuide().getId().equals(guideId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Action interdite");
        }
        
        if (reservation.getStatut() != StatutReserv.EN_ATTENTE) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Cette réservation a déjà été traitée");
        }
        
        // Changer le statut
        reservation.setStatut(StatutReserv.REFUSEE);
        reservationRepo.save(reservation);
        
     // Notifier le touriste
        notificationService.creerNotification(
            reservation.getVisiteur().getId(),
            "Désolé, le guide " + 
            reservation.getGuide().getUsername() + 
            " a refusé votre réservation. Cherchez un autre guide !"
        );
        
        return ResponseEntity.ok("Réservation refusée");
    }


    
    
    
    
    
    
    
    
    
    
    
    
    
    

}
