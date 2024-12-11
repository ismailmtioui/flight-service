package com.example.Flight_service.web;

import com.example.Flight_service.Entity.Flight;
import com.example.Flight_service.Entity.FlightClass;
import com.example.Flight_service.service.FlightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/flights")
public class FlightController {

    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }
    @PostMapping
    public ResponseEntity<Flight> createFlight(@RequestBody Flight flight) {

        if (flight.getFlightClasses() != null && !flight.getFlightClasses().isEmpty()) {

            for (FlightClass flightClass : flight.getFlightClasses()) {
                flightClass.setFlight(flight);
            }
        }
        Flight savedFlight = flightService.saveFlight(flight);
        return new ResponseEntity<>(savedFlight, HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Flight> getFlightById(@PathVariable Long id) {
        Optional<Flight> flight = flightService.getFlightById(id);
        return flight.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<List<Flight>> getAllFlights() {
        List<Flight> flights = flightService.getAllFlights();
        return new ResponseEntity<>(flights, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFlight(@PathVariable Long id) {
        if (flightService.flightExists(id)) {
            flightService.deleteFlight(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<Flight> updateFlight(@PathVariable Long id, @RequestBody Flight flight) {
        if (flightService.flightExists(id)) {

            if (flight.getFlightClasses() != null && !flight.getFlightClasses().isEmpty()) {
                for (FlightClass flightClass : flight.getFlightClasses()) {
                    flightClass.setFlight(flight);
                }
            }

            Flight updatedFlight = flightService.saveFlight(flight);
            return new ResponseEntity<>(updatedFlight, HttpStatus.OK);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/live")
    public ResponseEntity<Map<String, Object>> getLiveFlightsFromOpenSky() {
        String openSkyApiUrl = "https://opensky-network.org/api/states/all";

        RestTemplate restTemplate = new RestTemplate();

        try {
            // Fetch data from OpenSky API
            Map<String, Object> response = restTemplate.getForObject(openSkyApiUrl, Map.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Handle errors (e.g., connectivity issues)
            return new ResponseEntity<>(Map.of("error", "Unable to fetch flight data"), HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
}
