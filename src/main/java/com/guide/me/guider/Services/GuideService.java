package com.guide.me.guider.Services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.guide.me.guider.DTOs.GuideAvisResponse;
import com.guide.me.guider.DTOs.GuideProfileResponse;
import com.guide.me.guider.DTOs.GuidePublicDTO;
import com.guide.me.guider.DTOs.Mapper;
import com.guide.me.guider.DTOs.UpdateGuideProfileRequest;
import com.guide.me.guider.DTOs.GuideResponse;
import com.guide.me.guider.Repositories.AvisRepository;
import com.guide.me.guider.Repositories.GuideRepository;
import com.guide.me.guider.entities.Avis;
import com.guide.me.guider.entities.Guide;

import java.util.List;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class GuideService {

    private final GuideRepository guideRepo;
    private final AvisRepository avisRepo;

    public List<GuideResponse> getGuidesByPays(Long paysId) {
    	return guideRepo.findAllGuidesByPays(paysId)
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }
    
    public List<GuideResponse> getGuidesByPaysAndVilleNom(Long paysId, String villeNom) {
    	return guideRepo.findAllGuidesByPaysAndVilleNom(paysId, villeNom)
                .stream()
                .map(Mapper::toDTO)
                .toList();
    }
    
    /**
     * VOIR MON PROFIL (Guide connecté)
     */
    public ResponseEntity<?> getMonProfil(Long guideId) {
        Guide guide = guideRepo.findById(guideId)
            .orElseThrow(() -> new RuntimeException("Guide introuvable"));
        
        GuideProfileResponse response = new GuideProfileResponse(
            guide.getId(),
            guide.getUsername(),
            guide.getAge(),
            guide.getEmail(),
            guide.getTelephone(),
            guide.getDescription(),
            guide.getLangues(),
            guide.getVille().getNom(),
            guide.getVille().getPays().getNom(),
            guide.getPrixDemiJournee(),
            guide.getPrixJournee(),
            guide.getNoteMoyenne(),
            guide.getAvis() != null ? guide.getAvis().size() : 0
        );
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * VOIR PROFIL PUBLIC d'un guide (pour les touristes)
     */
    public ResponseEntity<?> getProfilPublic(Long guideId) {
        Guide guide = guideRepo.findById(guideId)
            .orElseThrow(() -> new RuntimeException("Guide introuvable"));
        
        GuidePublicDTO response = new GuidePublicDTO(
            guide.getId(),
            guide.getUsername(),
            guide.getAge(),
            guide.getDescription(),
            guide.getLangues(),
            guide.getVille().getNom(),
            guide.getVille().getPays().getNom(),
            guide.getPrixDemiJournee(),
            guide.getPrixJournee(),
            guide.getNoteMoyenne(),
            guide.getAvis() != null ? guide.getAvis().size() : 0
        );
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * MODIFIER MON PROFIL
     */
    public ResponseEntity<?> modifierMonProfil(
            Long guideId, 
            UpdateGuideProfileRequest request) {
        
        Guide guide = guideRepo.findById(guideId)
            .orElseThrow(() -> new RuntimeException("Guide introuvable"));
        
        // Mettre à jour les champs
        guide.setDescription(request.getDescription());
        guide.setLangues(request.getLangues());
        guide.setPrixDemiJournee(request.getPrixDemiJournee());
        guide.setPrixJournee(request.getPrixJournee());
        
        guideRepo.save(guide);
        
        return ResponseEntity.ok("Profil mis à jour avec succès");
    }
    
    /**
     * VOIR MES AVIS (Guide connecté)
     */
    public ResponseEntity<List<GuideAvisResponse>> getMesAvis(Long guideId) {
        List<Avis> avisList = avisRepo.findByGuideIdOrderByDateDesc(guideId);
        
        List<GuideAvisResponse> response = avisList.stream()
            .map(avis -> new GuideAvisResponse(
                avis.getId(),
                avis.getVisiteur().getUsername(),
                avis.getNote(),
                avis.getCommentaire(),
                avis.getDate()
            ))
            .toList();
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * VOIR AVIS d'un guide (PUBLIC - pour les touristes)
     */
    public ResponseEntity<List<GuideAvisResponse>> getAvisPublic(Long guideId) {
        if (!guideRepo.existsById(guideId)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        
        List<Avis> avisList = avisRepo.findByGuideIdOrderByDateDesc(guideId);
        
        List<GuideAvisResponse> response = avisList.stream()
            .map(avis -> new GuideAvisResponse(
                avis.getId(),
                avis.getVisiteur().getUsername(),
                avis.getNote(),
                avis.getCommentaire(),
                avis.getDate()
            ))
            .toList();
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * LISTE DE TOUS LES GUIDES (PUBLIC)
     */
    public ResponseEntity<List<Guide>> getAllGuides() {
        List<Guide> guides = guideRepo.findAll();
        return ResponseEntity.ok(guides);
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
