package com.guide.me.guider.DTOs;

import com.guide.me.guider.entities.Disponibilite;
import com.guide.me.guider.entities.Guide;

public class Mapper {
	
	public static GuideResponse toDTO(Guide guide) {
        GuideResponse dto = new GuideResponse();
        dto.setId(guide.getId());
        dto.setNom(guide.getUsername());
        dto.setLangues(guide.getLangues());
        dto.setVille(guide.getVille().getNom());
        dto.setPrixDemiJournee(guide.getPrixDemiJournee());
        dto.setPrixJournee(guide.getPrixJournee());
        dto.setNoteMoyenne(guide.getNoteMoyenne());
        dto.setPaysId(guide.getVille().getPays().getId());
        return dto;
    }
	
	public static GuideProfileResponse toDetailsDTO ( Guide guide ) {
		GuideProfileResponse dto = new GuideProfileResponse();
        dto.setId(guide.getId());
        dto.setUsername(guide.getUsername());
        dto.setDescription(guide.getDescription());
        dto.setLangues(guide.getLangues());
        dto.setVille(guide.getVille().getNom());
        dto.setPays(guide.getVille().getPays().getNom());
        dto.setPrixDemiJournee(guide.getPrixDemiJournee());
        dto.setPrixJournee(guide.getPrixJournee());
        dto.setNoteMoyenne(guide.getNoteMoyenne());
        return dto;
	}
	
	public static DisponibiliteResponse toCalendarDTO(Disponibilite d) {
        return new DisponibiliteResponse(
                d.getDate(),
                d.getStatut()
        );
    }
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	

}
