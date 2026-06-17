package com.guide.me.guider.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guide.me.guider.entities.Avis;

import java.util.List;

@Repository
public interface AvisRepository extends JpaRepository<Avis, Long> {
	
    boolean existsByReservationId(Long reservationId);
    
 // Trouver par guide
    List<Avis> findByGuideId(Long guideId);
    
    // Trouver par guide (triés par date décroissante)
    List<Avis> findByGuideIdOrderByDateDesc(Long guideId);
    
    // Trouver par visiteur
    List<Avis> findByVisiteurId(Long visiteurId);
    
}

