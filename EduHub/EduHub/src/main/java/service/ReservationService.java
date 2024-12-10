package service;

import model.Reservation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import jakarta.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class ReservationService {

    private final WebClient webClient;
    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    private List<Reservation> reservations = new ArrayList<>();

    public ReservationService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://mocki.io/v1").build();
    }

    @PostConstruct
    public void initializeReservations() {
        try {
            fetchReservationsFromApi();
        } catch (Exception e) {
            logger.error("Failed to fetch reservations from API. Loading fallback data.", e);
            loadFallbackReservations();  // Load fallback data if fetching API fails
        }
    }

    // Fetch reservations from external API
    public void fetchReservationsFromApi() {
        try {
            logger.info("Fetching reservations from external API...");
            Mono<Reservation[]> response = this.webClient.get()
                    .uri("/5bf52805-a7ad-42fe-ab14-e546dfd363c5")
                    .retrieve()
                    .bodyToMono(Reservation[].class);

            Reservation[] fetchedReservations = response.block();

            if (fetchedReservations == null || fetchedReservations.length == 0) {
                logger.warn("No reservations were fetched from the API.");
                return;
            }

            reservations.addAll(Arrays.asList(fetchedReservations));

            logger.info("Reservations fetched successfully from the API.");
        } catch (WebClientResponseException e) {
            logger.error("Error fetching API data: {}", e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error: {}", e.getMessage());
        }
    }

    // Fallback data
    private void loadFallbackReservations() {
        reservations.clear();
        reservations.add(new Reservation("A01", 5, true));
        reservations.add(new Reservation("B02", 3, true));
        reservations.add(new Reservation("C03", 2, true));
        reservations.add(new Reservation("D04", 4, true));
        reservations.add(new Reservation("E05", 6, true));
        reservations.add(new Reservation("F06", 7, true));
        reservations.add(new Reservation("G07", 2, true));
        reservations.add(new Reservation("H08", 5, true));
        reservations.add(new Reservation("I09", 10, true));
        reservations.add(new Reservation("J10", 4, true));

        logger.info("Fallback reservations (all rooms reserved) loaded.");
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }

    // Update reservation status by classroom name
    public boolean updateReservation(String classroomName, boolean isReserved) {
        for (Reservation reservation : reservations) {
            if (reservation.getClassroomName().equals(classroomName)) {
                reservation.setReserved(isReserved);
                return true;
            }
        }
        return false;
    }

    // Add new reservation
    public boolean addReservation(Reservation reservation) {
        return reservations.add(reservation);
    }
}
