package com.example.Flight_service.Entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
public class FlightClass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private FlightClassType flightClassType;  // Type of the flight class (e.g., Economy, Business, First)

    private Double price;  // Price for this class

    @ManyToOne
    @JoinColumn(name = "flight_id")
    @JsonBackReference// Foreign key for Flight
    private Flight flight;  // Reference to the associated Flight

    // Getters and Setters
    public Flight getFlight() {
        return flight;
    }

    public void setFlight(Flight flight) {
        this.flight = flight;
    }

    public FlightClassType getFlightClassType() {
        return flightClassType;
    }

    public void setFlightClassType(FlightClassType flightClassType) {
        this.flightClassType = flightClassType;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}
