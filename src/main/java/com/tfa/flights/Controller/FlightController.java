package com.tfa.flights.Controller;

import com.tfa.flights.Service.DataStoreClient;
import com.tfa.flights.dto.FlightDTO;
import com.tfa.flights.dto.FlightDTOResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/flights")
public class FlightController {
    private static final Logger logger = LogManager.getLogger(FlightController.class);

    @Autowired
    private DataStoreClient dataStoreClient;

    @PostMapping
    public ResponseEntity<?> createFlight(@RequestBody FlightDTO flightDTO) {
        try {
            FlightDTOResponse addedFlight = dataStoreClient.createFlight(flightDTO);

            return ResponseEntity.status(HttpStatus.CREATED).body(addedFlight);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to add flight: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity< List<FlightDTOResponse>> getAllFlights() {
        try {
            List<FlightDTOResponse> flights = dataStoreClient.getAllFlights();
            return ResponseEntity.status(HttpStatus.OK).body(flights);

        } catch (RuntimeException e) {
           // return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to retrieve user: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }
//    @GetMapping("/{id}")
//    public ResponseEntity<?> getUser(@PathVariable Long id) throws Exception {
//        // Use Optional to safely handle user retrieval
//        try {
//            FlightDTOResponse flight = dataStoreClient.getUserById(id);
//
//            return ResponseEntity.status(HttpStatus.OK)
//                    .body(flight) ;
//        } catch (RuntimeException e) {
//            // Return NOT_FOUND if the user does not exist
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update user: Invalid ID or Flight details");
//
//        }
//    }

    // The second method explicitly defines the return type as ResponseEntity<FlightDTOResponse>, ensuring that only objects of this type are returned.
    //  The first method uses a generic ResponseEntity<?>, which sacrifices type safety and requires additional casting or checks when working with the response.    //The second method (ResponseEntity<FlightDTOResponse> with type safety) is more efficient and preferable in a professional environment.
    @GetMapping("/{id}")
    public ResponseEntity<FlightDTOResponse> getUser(@PathVariable Long id) {
        // Use Optional to safely handle user retrieval
        try {
            FlightDTOResponse user = dataStoreClient.getUserById(id);

            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e) {
            // Return NOT_FOUND if the user does not exist
            logger.error("An error occurred: {}", e.getMessage());

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateFlight(@PathVariable Long id, @RequestBody FlightDTO flightDTO) {
        try {
            FlightDTOResponse updatedFlight = dataStoreClient.updateUser(id, flightDTO);
            return ResponseEntity.ok(updatedFlight);
        } catch (RuntimeException e) {
            logger.error("Failed to update flight: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Failed to update flight: Invalid ID or Flight details");
        }

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFlight(@PathVariable Long id) {
        try {
            String message = dataStoreClient.deleteFlight(id);

            return ResponseEntity.ok(message);
        } catch (RuntimeException e) {
            // Handle runtime exceptions
            logger.error("Unexpected error occurred: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id not found");
        }
    }
}
