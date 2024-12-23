package com.example.Flight_service.FlightRepository;

import com.example.Flight_service.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface FlightRepository extends JpaRepository<Flight, Long> {
    @Query("SELECT f FROM Flight f WHERE f.departure = :departure AND f.destination = :destination AND f.schedule = :date")
    List<Flight> findByDepartureAndDestinationAndDate(
            @Param("departure") String departure,
            @Param("destination") String destination,
            @Param("date") String date);
}
