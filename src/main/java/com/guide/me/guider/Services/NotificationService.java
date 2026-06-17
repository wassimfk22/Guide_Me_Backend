package com.guide.me.guider.Services;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.guide.me.guider.DTOs.NotificationResponse;
import com.guide.me.guider.Repositories.NotificationRepository;
import com.guide.me.guider.Repositories.UtilisateurRepository;
import com.guide.me.guider.entities.Notification;
import com.guide.me.guider.entities.Utilisateur;

import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class NotificationService {
    
    private final NotificationRepository notifRepo;
    private final UtilisateurRepository utilisateurRepo;
    
    /**
     * CRÉER UNE NOTIFICATION
     */
    public void creerNotification(Long utilisateurId, String message) {
        Utilisateur user = utilisateurRepo.findById(utilisateurId)
            .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));
        
        Notification notif = new Notification();
        notif.setUtilisateur(user);
        notif.setMessage(message);
        notif.setDateEnvoi(LocalDateTime.now());
        
        notifRepo.save(notif);
    }
    
    /**
     * VOIR MES NOTIFICATIONS
     */
    public ResponseEntity<List<NotificationResponse>> getMesNotifications(Long userId) {
        List<Notification> notifications = notifRepo.findByUtilisateurIdOrderByDateEnvoiDesc(userId);
        
        List<NotificationResponse> response = notifications.stream()
            .map(notif -> new NotificationResponse(
                notif.getId(),
                notif.getMessage(),
                notif.getDateEnvoi()
            ))
            .toList();
        
        return ResponseEntity.ok(response);
    }
    
    /**
     * SUPPRIMER UNE NOTIFICATION
     */
    public ResponseEntity<String> supprimerNotification(Long notificationId, Long userId) {
        Notification notif = notifRepo.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification introuvable"));
        
        // Vérifier que c'est bien sa notification
        if (!notif.getUtilisateur().getId().equals(userId)) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body("Action interdite");
        }
        
        notifRepo.delete(notif);
        return ResponseEntity.ok("Notification supprimée");
    }
    
    /**
     * SUPPRIMER TOUTES MES NOTIFICATIONS
     */
    public ResponseEntity<String> supprimerToutesNotifications(Long userId) {
        List<Notification> notifications = notifRepo.findByUtilisateurIdOrderByDateEnvoiDesc(userId);
        notifRepo.deleteAll(notifications);
        
        return ResponseEntity.ok(notifications.size() + " notification(s) supprimée(s)");
    }
    
    /**
     * COMPTER MES NOTIFICATIONS NON LUES
     * (Si tu ajoutes un champ "lu" dans l'entity)
     */
    public ResponseEntity<Long> compterNonLues(Long userId) {
        long count = notifRepo.countByUtilisateurId(userId);
        return ResponseEntity.ok(count);
    }
    
    
    
}