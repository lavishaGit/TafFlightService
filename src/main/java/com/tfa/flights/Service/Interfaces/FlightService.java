package com.tfa.flights.Service.Interfaces;

import com.tfa.flights.dto.FlightDTO;
import com.tfa.flights.dto.FlightDTOResponse;

import java.util.List;

public interface FlightService {
    public FlightDTOResponse createFlight(FlightDTO flightDTO);
    public FlightDTOResponse getUserById(Long id);
    public FlightDTOResponse updateUser(Long id, FlightDTO flightDTO);
    public List<FlightDTOResponse> getAllFlights();
    public String deleteFlight(Long id);

}
