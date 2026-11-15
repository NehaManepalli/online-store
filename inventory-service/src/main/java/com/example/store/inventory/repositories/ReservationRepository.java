package com.example.store.inventory.repositories;

import com.example.store.inventory.entities.Reservation;
import com.example.store.inventory.entities.ReservationStatus;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
    boolean existsByOrderId(Long id);
    List<Reservation> findByOrderId(Long id);

    @Modifying
    @Query("update Reservation r set r.status = :toStatus where r.id = :id and r.status = :fromStatus ")
    int releaseReservation(@Param("id") Long id, @Param("fromStatus") ReservationStatus fromStatus, @Param("toStatus") ReservationStatus toStatus);
}
