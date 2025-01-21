package com.tfa.flights.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FlightDTOResponse {
    private Long id;
    private String flightNumber;
    private String departure;
    private String arrival;
  //  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime departureTime;
  //  @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime arrivalTime;
    private Double price;
    private Integer availableSeats;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


}
