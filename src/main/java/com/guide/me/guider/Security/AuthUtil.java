package com.guide.me.guider.Security;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import com.guide.me.guider.Repositories.UtilisateurRepository;
import com.guide.me.guider.entities.Utilisateur;

@Component
public class AuthUtil {
	
	private final UtilisateurRepository utilisateurRepo;
    
    public AuthUtil(UtilisateurRepository utilisateurRepo) {
        this.utilisateurRepo = utilisateurRepo;
    }
    
    public Long getUserIdFromAuth(Authentication auth) {
        if (auth == null || auth.getName() == null) {
            throw new RuntimeException("Utilisateur non authentifié");
        }
        
        String username = auth.getName();
        Utilisateur user = utilisateurRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        
        return user.getId();
    }
    
    public Utilisateur getUserFromAuth(Authentication auth) {
        if (auth == null || auth.getName() == null) {
            throw new RuntimeException("Utilisateur non authentifié");
        }
        
        String username = auth.getName();
        return utilisateurRepo.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
    }

}
