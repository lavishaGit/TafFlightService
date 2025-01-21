package com.tfa.flights.Service;

import com.tfa.flights.Service.Interfaces.FlightService;
import com.tfa.flights.dto.FlightDTO;
import com.tfa.flights.dto.FlightDTOResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DataStoreClient implements FlightService {

//    Endpoints:
//
//            •	GET /flights: Retrieve all available flights.
//
//            •	  GET /flights/{flightId}: Get details of a specific flight.
//
//            •	  POST /flights: Add a new flight.
//
//	  •	  PUT /flights/{flightId}: Update flight details .
//
//	  •	  DELETE /flights/{flightId}: Delete a flight.

    private static final Logger logger = LogManager.getLogger(DataStoreClient.class);

    @Autowired
    private RestTemplate restTemplate;
    @Value("${datastore.service.url}")
    private String dataStoreURL;

    public FlightDTOResponse createFlight(FlightDTO flightDTO) {
        if (flightDTO == null) {
            throw new IllegalArgumentException("Flight fields are missing ");
        }
        FlightDTOResponse flightresponse = restTemplate.postForObject(dataStoreURL, flightDTO, FlightDTOResponse.class);
        System.out.println(flightresponse);
        if (flightresponse == null) {
            logger.error("Response is null");
            throw new RuntimeException(" Response is null");

        }
        // Log successful created flight
        logger.info("Flight created successfully: " + flightresponse.getId());
        return flightresponse;
    }


    public FlightDTOResponse getUserById(Long id) {
        FlightDTOResponse response = restTemplate.getForObject(dataStoreURL + "/" + id, FlightDTOResponse.class);
        if (response == null) {
            logger.error("Could not retrieve response");
            throw new RuntimeException("Failed to retrieve flight, received null response");

        }
        logger.info("Received User by Id");
        return response;
    }

    public FlightDTOResponse updateUser(Long id, FlightDTO flightDTO) {
        if (flightDTO == null) {
            throw new IllegalArgumentException("Flight  Data is null");
        }
        try {
            FlightDTOResponse responseUser = restTemplate.getForObject(dataStoreURL + "/" + id, FlightDTOResponse.class);

            if (!responseUser.getId().equals(id)) {
                throw new NoSuchElementException("Flight with ID " + id + " does not exist");
            } // Log the update attempt
            logger.info("Updatingflight with ID: " + id);


            restTemplate.put(dataStoreURL + "/" + id, flightDTO, FlightDTOResponse.class);
            // Log success
            logger.info("Flight with ID: " + id + " successfully updated");
        } catch (RestClientException e) {
            // Handle HTTP/communication errors
            logger.error("Error during flight update", e);
            throw new RuntimeException("Failed to update flight due to network issues", e);
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("Unexpected error during flight update", e);
            throw new RuntimeException("Unexpected error occurred while updating the flight", e);
        }

        return restTemplate.getForObject(dataStoreURL + "/" + id, FlightDTOResponse.class);  // Getting the updated user info

    }

    public List<FlightDTOResponse> getAllFlights() {
        try {
            // Perform the get request
            FlightDTOResponse[] flights = restTemplate.getForObject(dataStoreURL, FlightDTOResponse[].class);
            if (flights == null || flights.length == 0) {
                logger.warn("No flights found");
                return Collections.emptyList();
            }
            // Log success
            logger.info("Retrieved all flights successfully");
            return List.of(flights);
        } catch (RestClientException e) {
            // Handle HTTP/communication errors
            logger.error("Error retrieving all flights", e);
            throw new RuntimeException("Failed to retrieve flights due to network issues", e);
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("Unexpected error during retrieving all flights", e);
            throw new RuntimeException("Unexpected error occurred while retrieving flights", e);
        }
    }

    public String deleteFlight(Long id) {
        try {
            FlightDTOResponse flight = restTemplate.getForObject(dataStoreURL, FlightDTOResponse.class);

            if (flight == null) {
                // Flight does not exist
                return "Flight with ID: " + id + " does not exist.";
            }
            // Perform the delete request
            restTemplate.delete(dataStoreURL + "/" + id);
            // Log success
            logger.info("Flight with ID: " + id + " successfully deleted");
            return "Flight with ID:" + id + " successfully deleted";

        } catch (RestClientException e) {
            // Handle HTTP/communication errors
            logger.error("Error during flight deletion", e.getMessage());
            throw new RuntimeException("Failed to delete flight due to network issues", e);
        } catch (Exception e) {
            // Handle other exceptions
            logger.error("Unexpected error during flight deletion", e.getMessage());
            throw new RuntimeException("Unexpected error occurred while deleting the flight", e);
        }

    }
}
