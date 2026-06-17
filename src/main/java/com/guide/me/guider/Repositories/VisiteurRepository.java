package com.guide.me.guider.Repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guide.me.guider.entities.Visiteur;

@Repository
public interface VisiteurRepository extends JpaRepository<Visiteur, Long> {
	
	
}
