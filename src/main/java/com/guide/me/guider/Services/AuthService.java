package com.guide.me.guider.Services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guide.me.guider.DTOs.*;
import com.guide.me.guider.Repositories.*;
import com.guide.me.guider.Security.JwtUtil;
import com.guide.me.guider.entities.*;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthService {
    
    private final UtilisateurRepository utilisateurRepo;
    private final GuideRepository guideRepo;
    private final VisiteurRepository visiteurRepo;
    private final VilleRepository villeRepo;
    private final PaysRepository paysRepo;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AuthenticationManager authManager;
    private final JwtUtil jwtUtil;
    
    /**
     * LOGIN
     */
    public ResponseEntity<?> login(LoginRequest request) {
        try {
            // Authentifier avec Spring Security
            Authentication auth = authManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.getUsername(), 
                    request.getPassword()
                )
            );
            
            // Récupérer l'utilisateur
            Utilisateur user = utilisateurRepo.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
            
            // Générer le token JWT
            String token = jwtUtil.generateToken(user.getUsername());
            
            // Créer la réponse
            LoginResponse response = new LoginResponse(
                token,
                user.getId(),
                user.getUsername(),
                user.getRole(),
                "Connexion réussie"
            );
            
            return ResponseEntity.ok(response);
            
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Username ou mot de passe incorrect");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Erreur lors de la connexion: " + e.getMessage());
        }
    }
    
    /**
     * REGISTER GUIDE
     */
    public ResponseEntity<?> registerGuide(RegisterGuideRequest request) {
            // Vérifier si username existe
            if (utilisateurRepo.findByUsername(request.getUsername()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Ce username est déjà utilisé");
            }
            
            // Vérifier si email existe
            if (utilisateurRepo.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CREATED)
                    .body("Cet email est déjà utilisé");
            }
            
            // Vérifier que la ville existe
            Ville ville = villeRepo.findById(request.getVilleId())
                .orElseThrow(() -> new RuntimeException("Ville introuvable"));
            
            // Créer le guide
            Guide guide = new Guide();
            guide.setUsername(request.getUsername());
            guide.setAge(request.getAge());
            guide.setEmail(request.getEmail());
            guide.setPassword(passwordEncoder.encode(request.getPassword()));
            guide.setTelephone(request.getTelephone());
            guide.setRole(RoleUser.GUIDER);
            guide.setDescription(request.getDescription());
            guide.setLangues(request.getLangues());
            guide.setVille(ville);
            guide.setPrixDemiJournee(request.getPrixDemiJournee());
            guide.setPrixJournee(request.getPrixJournee());
            guide.setNoteMoyenne(0.0);
            
            Guide saved = guideRepo.save(guide);
            
            // Générer token
            String token = jwtUtil.generateToken(saved.getUsername());
            
            // Créer réponse
            LoginResponse response = new LoginResponse(
                token,
                saved.getId(),
                saved.getUsername(),
                saved.getRole(),
                "Inscription réussie ! Bienvenue " + saved.getUsername()
            );
            
            return ResponseEntity.status(HttpStatus.OK).body(response);
            
    }
    
    /**
     * REGISTER TOURISTE
     */
    public ResponseEntity<?> registerTouriste(RegisterTouristeRequest request) {
            // Vérifier si username existe
            if (utilisateurRepo.findByUsername(request.getUsername()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Ce username est déjà utilisé");
            }
            
            // Vérifier si email existe
            if (utilisateurRepo.findByEmail(request.getEmail()).isPresent()) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Cet email est déjà utilisé");
            }
            
            // Vérifier que le pays existe
            Pays pays = paysRepo.findById(request.getPaysOrigineId())
                .orElseThrow(() -> new RuntimeException("Pays introuvable"));
            
            // Créer le touriste
            Visiteur touriste = new Visiteur();
            touriste.setUsername(request.getUsername());
            touriste.setAge(request.getAge());
            touriste.setEmail(request.getEmail());
            touriste.setPassword(passwordEncoder.encode(request.getPassword()));
            touriste.setTelephone(request.getTelephone());
            touriste.setRole(RoleUser.TOURIST);
            touriste.setPaysOrigine(pays);
            
            Visiteur saved = visiteurRepo.save(touriste);
            
            // Générer token
            String token = jwtUtil.generateToken(saved.getUsername());
            
            // Créer réponse
            LoginResponse response = new LoginResponse(
                token,
                saved.getId(),
                saved.getUsername(),
                saved.getRole(),
                "Inscription réussie ! Bienvenue " + saved.getUsername()
            );
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
    }
}