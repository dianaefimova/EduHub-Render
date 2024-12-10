package controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import service.ReservationService;
import model.Reservation;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public-api/reservation")
public class ReservationController {

    private static final Logger logger = LoggerFactory.getLogger(ReservationController.class); // Logger

    private final ReservationService reservationService;

    public ReservationController(ReservationService reservationService) {
        this.reservationService = reservationService;
    }

    // GET all reservations
    @GetMapping
    public List<Reservation> getAllReservations() {
        return reservationService.getAllReservations();
    }

    // POST a new reservation
    @PostMapping
    public boolean addReservation(@RequestBody Reservation reservation) {
        return reservationService.addReservation(reservation);
    }

    // PUT update a reservation by classroom name
    @PutMapping("/reservations/{classroomName}")
    public ResponseEntity<String> updateReservation(
            @PathVariable String classroomName,
            @RequestBody Reservation updatedReservation) {

        logger.info("Updating reservation for classroom: {}", classroomName);

        boolean success = reservationService.updateReservation(classroomName, updatedReservation.isReserved());
        if (success) {
            return ResponseEntity.ok("Reservation updated successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Reservation not found for classroom: " + classroomName);
        }
    }

    @GetMapping("/fetch")
    public void fetchReservations() {
        logger.info("Fetching reservations from external API...");
        reservationService.fetchReservationsFromApi();
    }

}
