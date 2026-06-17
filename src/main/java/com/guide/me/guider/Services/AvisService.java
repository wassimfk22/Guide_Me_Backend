package com.guide.me.guider.Services;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import com.guide.me.guider.DTOs.AvisRequest;
import com.guide.me.guider.Repositories.AvisRepository;
import com.guide.me.guider.Repositories.DisponibiliteRepository;
import com.guide.me.guider.Repositories.GuideRepository;
import com.guide.me.guider.Repositories.NotificationRepository;
import com.guide.me.guider.Repositories.ReservationRepository;
import com.guide.me.guider.Repositories.VisiteurRepository;
import com.guide.me.guider.entities.Avis;
import com.guide.me.guider.entities.Guide;
import com.guide.me.guider.entities.Reservation;
import com.guide.me.guider.entities.ReservationDate;
import com.guide.me.guider.entities.StatutReserv;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AvisService {
	
	private final ReservationRepository reservationRepo;
	private final AvisRepository avisRepo;
	private final GuideRepository guideRepo;
	private final NotificationService notificationService;
	
	public void noterGuide(AvisRequest request, Long visiteurId) {

	    Reservation reservation = reservationRepo.findById(request.reservationId())
	        .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

	    // 1️⃣ Sécurité : le touriste est bien le propriétaire
	    if (!reservation.getVisiteur().getId().equals(visiteurId)) {
	        throw new RuntimeException("Action interdite");
	    }
	    // 2️⃣ Réservation confirmée
	    if (reservation.getStatut() != StatutReserv.CONFIRMEE) {
	        throw new RuntimeException("Réservation non confirmée");
	    }
	    // 3️⃣ Pas déjà notée
	    if (avisRepo.existsByReservationId(reservation.getId())) {
	        throw new RuntimeException("Guide déjà noté pour cette réservation");
	    }

	    // 4️⃣ Dates passées
	    LocalDate lastDate =
	        reservation.getDates().stream()
	            .map(ReservationDate::getDate)
	            .max(LocalDate::compareTo)
	            .orElseThrow();
	    if (lastDate.isAfter(LocalDate.now())) {
	        throw new RuntimeException("Vous ne pouvez noter qu'après la visite");
	    }

	    Avis avis = new Avis();
	    avis.setNote(request.note());
	    avis.setCommentaire(request.commentaire());
	    avis.setGuide(reservation.getGuide());
	    avis.setVisiteur(reservation.getVisiteur());
	    avis.setReservation(reservation);
	    avis.setDate(LocalDate.now());
	    avisRepo.save(avis);
	    
	 // Notifier le guide
        this.notificationService.creerNotification(
            reservation.getGuide().getId(),
            reservation.getVisiteur().getUsername() + 
            "a laissé un avis sur ta réservation"
        );

	    recalculerNoteGuide(reservation.getGuide().getId());
	}
	
	private void recalculerNoteGuide(Long guideId) {

	    List<Avis> avis = avisRepo.findByGuideId(guideId);

	    double moyenne = avis.stream()
	        .mapToInt(Avis::getNote)
	        .average()
	        .orElse(0);

	    Guide guide = guideRepo.getReferenceById(guideId);
	    guide.setNoteMoyenne(moyenne);
	}



}
