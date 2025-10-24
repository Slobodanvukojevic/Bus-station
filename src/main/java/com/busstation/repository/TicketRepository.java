package com.busstation.repository;

import com.busstation.model.Ticket;
import com.busstation.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    List<Ticket> findByUserOrderByPurchaseDateDesc(User user);

    @Query("""
        SELECT t FROM Ticket t
        WHERE t.user = :user AND t.status = :status
        ORDER BY t.purchaseDate DESC
    """)
    List<Ticket> findByUserAndStatus(@Param("user") User user,
                                     @Param("status") Ticket.Status status);

    @Query("""
        SELECT CASE WHEN COUNT(t) > 0 THEN TRUE ELSE FALSE END
        FROM Ticket t
        WHERE t.user = :user
          AND t.departure.id = :departureId
          AND t.status <> 'CANCELLED'
    """)
    boolean existsActiveForDeparture(@Param("user") User user,
                                     @Param("departureId") Long departureId);

    @Query("""
        SELECT COUNT(t) FROM Ticket t
        WHERE t.departure.id = :departureId
          AND t.status <> 'CANCELLED'
    """)
    long countSoldTicketsByDeparture(@Param("departureId") Long departureId);

    @Query("""
        SELECT t FROM Ticket t
        WHERE t.status = 'ACTIVE'
          AND (
                t.departure.date < CURRENT_DATE
                OR (t.departure.date = CURRENT_DATE AND t.departure.time < CURRENT_TIME)
              )
    """)
    List<Ticket> findExpiredActiveTickets();

    @Query("""
        SELECT COALESCE(SUM(t.priceAtPurchase), 0)
        FROM Ticket t
        WHERE t.departure.id = :departureId
          AND t.status <> 'CANCELLED'
    """)
    BigDecimal sumRevenueByDeparture(@Param("departureId") Long departureId);

    List<Ticket> findByPurchaseDateBetweenOrderByPurchaseDateDesc(LocalDateTime from, LocalDateTime to);
}
