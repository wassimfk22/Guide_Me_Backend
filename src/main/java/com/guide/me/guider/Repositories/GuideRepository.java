package com.guide.me.guider.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.guide.me.guider.entities.Guide;

import java.util.List;

@Repository
public interface GuideRepository extends JpaRepository<Guide, Long> {
	
	@Query("SELECT g FROM Guide g WHERE g.ville.pays.id = :paysId")
	List<Guide> findAllGuidesByPays(@Param("paysId") Long paysId);
	
	@Query("SELECT g FROM Guide g WHERE g.ville.pays.id = :paysId AND LOWER(g.ville.nom) LIKE LOWER(CONCAT('%', :villeNom, '%'))")
	List<Guide> findAllGuidesByPaysAndVilleNom(@Param("paysId") Long paysId,
	                                           @Param("villeNom") String villeNom);
    
}
