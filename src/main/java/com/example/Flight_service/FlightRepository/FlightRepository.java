package com.example.Flight_service.FlightRepository;

import com.example.Flight_service.Entity.Flight;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FlightRepository extends JpaRepository<Flight, Long> {

}
