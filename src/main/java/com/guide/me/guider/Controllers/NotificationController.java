package com.guide.me.guider.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.guide.me.guider.DTOs.NotificationResponse;
import com.guide.me.guider.Security.AuthUtil;
import com.guide.me.guider.Services.NotificationService;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {
    
    private final NotificationService notifService;
    private final AuthUtil authUtil;
    
    /**
     * VOIR MES NOTIFICATIONS
     * GET /api/notifications/me
     */
    @GetMapping("/me")
    public ResponseEntity<List<NotificationResponse>> getMesNotifications(Authentication auth) {
        Long userId = authUtil.getUserIdFromAuth(auth);
        return notifService.getMesNotifications(userId);
    }
    
    /**
     * SUPPRIMER UNE NOTIFICATION
     * DELETE /api/notifications/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<String> supprimerNotification(
            @PathVariable Long id,
            Authentication auth) {
        
        Long userId = authUtil.getUserIdFromAuth(auth);
        return notifService.supprimerNotification(id, userId);
    }
    
    /**
     * SUPPRIMER TOUTES MES NOTIFICATIONS
     * DELETE /api/notifications/all
     */
    @DeleteMapping("/all")
    public ResponseEntity<String> supprimerToutesNotifications(Authentication auth) {
        Long userId = authUtil.getUserIdFromAuth(auth);
        return notifService.supprimerToutesNotifications(userId);
    }
    
    /**
     * COMPTER MES NOTIFICATIONS
     * GET /api/notifications/count
     */
    @GetMapping("/count")
    public ResponseEntity<Long> compterNotifications(Authentication auth) {
        Long userId = authUtil.getUserIdFromAuth(auth);
        return notifService.compterNonLues(userId);
    }
    
    
    
}