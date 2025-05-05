package com.example.BookingService.repository;

import com.example.BookingService.entity.Booking;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Integer> {


    Optional<Booking> findByBookingIdAndName( Integer bookingId,
                                              String name);
    @Query("SELECT b FROM Booking b ORDER BY b.name")  // b = entity alias
        List<Booking> getCustom();

    Page<Booking> findAll(Pageable pageable);
    List<Booking> findAll(Sort sort);

}
