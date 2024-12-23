package com.example.Flight_service.service;

import com.example.Flight_service.Entity.Flight;
import com.example.Flight_service.Entity.FlightClass;
import com.example.Flight_service.FlightRepository.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    private final FlightRepository flightRepository;

    @Autowired
    public FlightService(FlightRepository flightRepository) {
        this.flightRepository = flightRepository;
    }

    // Create or Update Flight
    public Flight saveFlight(Flight flight) {
        // If flight has flightClasses, save those as well
        if (flight.getFlightClasses() != null && !flight.getFlightClasses().isEmpty()) {
            // Associate the flight classes with the flight
            for (FlightClass flightClass : flight.getFlightClasses()) {
                flightClass.setFlight(flight); // Set the flight reference for each flight class
            }
        }

        return flightRepository.save(flight); // Save flight along with its flight classes
    }


    public Optional<Flight> getFlightById(Long id) {
        return flightRepository.findById(id);
    }

    // Get all Flights
    public List<Flight> getAllFlights() {
        return flightRepository.findAll();
    }

    // Delete Flight by ID
    public void deleteFlight(Long id) {
        flightRepository.deleteById(id);
    }

    // Check if flight exists
    public boolean flightExists(Long id) {
        return flightRepository.existsById(id);
    }
    public List<Flight> filterFlights(String departure, String destination, String date) {
        return flightRepository.findByDepartureAndDestinationAndDate(departure, destination, date);
    }
}
