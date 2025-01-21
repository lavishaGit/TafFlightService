package com.tfa.flights.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTO {
    private String flightNumber;
    private String departure;
    private String arrival;
  private LocalDateTime departureTime;  //pattern = "yyyy-MM-dd HH:mm:ss"
    private LocalDateTime arrivalTime;//pattern = "yyyy-MM-dd HH:mm:ss"
    private Double price;
    private Integer availableSeats;
}
