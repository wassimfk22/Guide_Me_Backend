package com.guide.me.guider.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.guide.me.guider.entities.Disponibilite;
import com.guide.me.guider.entities.ReservationDate;
import com.guide.me.guider.entities.StatutDispo;

import java.time.LocalDate;
import java.util.List;

@Repository 
public interface DisponibiliteRepository extends JpaRepository<Disponibilite, Long> {
	
    @Query("""
            SELECT d
            FROM Disponibilite d
            WHERE d.guide.id = :guideId
            ORDER BY d.date
        """)
        List<Disponibilite> findAllByGuideId(Long guideId);
    
    @Query("""
    		SELECT d FROM Disponibilite d
    		WHERE d.guide.id = :guideId
    		AND d.date IN :dates
    		AND d.statut = 'LIBRE'
    		""")
    		List<Disponibilite> findDisponibilitesLibres(
    		        Long guideId,
    		        List<LocalDate> dates);
    
 // Trouver toutes les dispos d'un guide avec un statut
    List<Disponibilite> findByGuideIdAndStatut(Long guideId, StatutDispo statut);
    
    // Trouver par date
    List<Disponibilite> findByDate(LocalDate date);
    
    // Trouver par guide et date (pour éviter doublons)
    List<Disponibilite> findByGuideIdAndDate(Long guideId, LocalDate date);
    
    // Trouver toutes les dispos d'un guide triées par date
    List<Disponibilite> findByGuideIdOrderByDateAsc(Long guideId);
    
    
}
