package com.guide.me.guider.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guide.me.guider.entities.Pays;

@Repository
public interface PaysRepository extends JpaRepository<Pays, Long> {
	
	
	
}
