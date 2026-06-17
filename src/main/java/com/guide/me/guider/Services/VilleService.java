package com.guide.me.guider.Services;

import org.springframework.stereotype.Service;

import com.guide.me.guider.Repositories.VilleRepository;
import com.guide.me.guider.entities.Ville;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class VilleService {
	
	private final VilleRepository villeRepository;

    public Ville getVilleById(Long id) {
        return this.villeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ville non trouvée"));
    }

}
