package com.guide.me.guider.Repositories;

import org.springframework.stereotype.Repository;

import com.guide.me.guider.entities.Reservation;
import com.guide.me.guider.entities.StatutReserv;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    
    // Vérifier chevauchement réservations (pour touriste)
    @Query("""
        SELECT COUNT(r) > 0
        FROM Reservation r
        JOIN r.dates d
        WHERE r.guide.id = :guideId
          AND r.visiteur.id = :visiteurId
          AND d.date IN :dates
          AND r.statut <> 'REFUSEE'
          AND r.statut <> 'ANNULEE'
    """)
    boolean existsReservationOverlap(
        Long guideId,
        Long visiteurId,
        List<LocalDate> dates
    );
    
    // Réservations du touriste (triées par date décroissante)
    List<Reservation> findByVisiteurIdOrderByIdDesc(Long visiteurId);
    
    // NOUVELLES MÉTHODES POUR LE GUIDE
    
    // Toutes les réservations d'un guide
    List<Reservation> findByGuideIdOrderByIdDesc(Long guideId);
    
    // Réservations d'un guide par statut
    List<Reservation> findByGuideIdAndStatutOrderByIdDesc(Long guideId, StatutReserv statut);
    
    @Query("""
            SELECT r
            FROM Reservation r
            WHERE r.guide.id = :guideId
            AND r.statut = 'EN_ATTENTE'
            ORDER BY r.id DESC
        """)
        List<Reservation> findDemandesEnAttente(Long guideId);

        @Query("""
            SELECT r
            FROM Reservation r
            WHERE r.guide.id = :guideId
            AND r.statut = 'CONFIRMEE'
            ORDER BY r.id DESC
        """)
        List<Reservation> findReservationsConfirmees(Long guideId);
}
