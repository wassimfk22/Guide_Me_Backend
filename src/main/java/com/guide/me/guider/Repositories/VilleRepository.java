package com.guide.me.guider.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guide.me.guider.entities.Ville;

import java.util.List;

@Repository
public interface VilleRepository extends JpaRepository<Ville, Long> {
	
    List<Ville> findByPaysId(Long paysId);
    
}
