package com.guide.me.guider.Services;

import org.springframework.stereotype.Service;

import com.guide.me.guider.Repositories.PaysRepository;
import com.guide.me.guider.Repositories.VilleRepository;
import com.guide.me.guider.entities.Pays;
import com.guide.me.guider.entities.Ville;

import java.util.List;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class PaysService {
	
	private final PaysRepository paysRepository;
    private final VilleRepository villeRepository;
    
    public PaysService ( PaysRepository paysRepository, VilleRepository villeRepository ) {
    	this.paysRepository = paysRepository;
    	this.villeRepository = villeRepository;
    }

//    Consulter tous les pays
    public List<Pays> getAllPays() {
        return paysRepository.findAll();
    }
    

}
