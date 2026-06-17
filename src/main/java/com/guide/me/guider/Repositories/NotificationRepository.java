// NotificationRepository.java
package com.guide.me.guider.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.guide.me.guider.entities.Notification;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    
    // Trouver par utilisateur (triées par date décroissante)
    List<Notification> findByUtilisateurIdOrderByDateEnvoiDesc(Long utilisateurId);
    
    // Compter les notifications d'un utilisateur
    long countByUtilisateurId(Long utilisateurId);
    
}