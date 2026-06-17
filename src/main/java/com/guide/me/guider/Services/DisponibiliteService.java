package com.guide.me.guider.Services;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.guide.me.guider.Repositories.DisponibiliteRepository;
import com.guide.me.guider.Repositories.GuideRepository;
import com.guide.me.guider.entities.Disponibilite;
import com.guide.me.guider.entities.Guide;
import com.guide.me.guider.entities.StatutDispo;
import com.guide.me.guider.DTOs.DisponibiliteGuideRequest;
import com.guide.me.guider.DTOs.DisponibiliteResponse;
import com.guide.me.guider.DTOs.Mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class DisponibiliteService {
	
	private final DisponibiliteRepository dispoRepo;
	private final GuideRepository guideRepo;
	
	/**
     * Voir toutes les disponibilités d'un guide
     */

    public List<DisponibiliteResponse> getGuideCalendar(Long guideId) {

        return dispoRepo.findAllByGuideId(guideId)
                .stream()
                .map(Mapper::toCalendarDTO)
                .toList();
    }
    
    /**
     * Créer une nouvelle disponibilité pour un guide
     */
    public ResponseEntity<String> creerDisponibilite(
            Long guideId, 
            DisponibiliteGuideRequest request) {
        
        // Vérifier que le guide existe
        Guide guide = guideRepo.findById(guideId)
            .orElseThrow(() -> new RuntimeException("Guide introuvable"));
        
        // Vérifier que la date n'est pas dans le passé
        if (request.getDate().isBefore(LocalDate.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("Impossible d'ajouter une date passée");
        }
        
        // Vérifier si la disponibilité existe déjà pour cette date
        List<Disponibilite> existantes = dispoRepo.findByGuideIdAndDate(
            guideId, 
            request.getDate()
        );
        
        if (!existantes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Vous avez déjà une disponibilité pour cette date");
        }
        
        // Créer la disponibilité
        Disponibilite dispo = new Disponibilite();
        dispo.setGuide(guide);
        dispo.setDate(request.getDate());
        dispo.setStatut(StatutDispo.LIBRE);
        
        dispoRepo.save(dispo);
        
        return ResponseEntity.status(HttpStatus.OK)
            .body("Disponibilité ajoutée avec succès pour le " + request.getDate());
    }
    
    /**
     * Voir toutes mes disponibilités
     */
    public List<Disponibilite> getMesDisponibilites(Long guideId) {
        return dispoRepo.findByGuideIdOrderByDateAsc(guideId);
    }
    
    /**
     * Voir uniquement mes disponibilités LIBRES
     */
    public ResponseEntity<List<Disponibilite>> getMesDisponibilitesLibres(Long guideId) {
    	Optional <Guide> guide = this.guideRepo.findById(guideId);
    	if ( guide.isEmpty() ) {
    		return new ResponseEntity<> (new ArrayList <Disponibilite> (),  HttpStatus.NOT_FOUND);
    	} else {
    		List<Disponibilite> dispos = dispoRepo.findByGuideIdAndStatut(guideId, StatutDispo.LIBRE);
    		return ResponseEntity.ok(dispos);
    	}
    }
    
    /**
     * Supprimer une disponibilité
     */
    public ResponseEntity<String> supprimerDisponibilite(
            Long disponibiliteId, 
            Long guideId) {
        
        Disponibilite dispo = dispoRepo.findById(disponibiliteId)
            .orElseThrow(() -> new RuntimeException("Disponibilité introuvable"));
        
        // Vérifier que c'est bien son propre guide
        if (!dispo.getGuide().getId().equals(guideId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Action interdite");
        }
        
        // Vérifier qu'elle n'est pas déjà réservée
        if (dispo.getStatut() == StatutDispo.RESERVE) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Cette disponibilité est réservée");
        }
        dispoRepo.delete(dispo);
        return ResponseEntity.ok("Disponibilité supprimée avec succès");
    }
    
    /**
     * Supprimer plusieurs disponibilités d'un coup
     */
    public ResponseEntity<String> supprimerPlusieursDisponibilites(
            List<Long> disponibiliteIds, 
            Long guideId) {
        
        int supprimees = 0;
        int echouees = 0;
        
        for (Long id : disponibiliteIds) {
            try {
                Disponibilite dispo = dispoRepo.findById(id).orElse(null);
                
                if (dispo == null) {
                    echouees++;
                    continue;
                }
                
                if (!dispo.getGuide().getId().equals(guideId)) {
                    echouees++;
                    continue;
                }
                
                if (dispo.getStatut() == StatutDispo.RESERVE) {
                    echouees++;
                    continue;
                }
                
                dispoRepo.delete(dispo);
                supprimees++;
                
            } catch (Exception e) {
                echouees++;
            }
        }
        
        if (echouees == 0) {
            return ResponseEntity.ok(
                supprimees + " disponibilité(s) supprimée(s) avec succès"
            );
        } else {
            return ResponseEntity.ok(
                supprimees + " disponibilité(s) supprimée(s), " + 
                echouees + " échec(s) (déjà réservées ou introuvables)"
            );
        }
    }
    
    

}
