package com.clown.frontoffice.controller;

import com.clown.frontoffice.model.Hotel;
import com.clown.frontoffice.model.Reservation;
import com.clown.frontoffice.service.HotelService;
import com.clown.frontoffice.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/public")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:5173", "http://localhost:8081"})
public class SearchController {
    
    @Autowired
    private HotelService hotelService;
    
    @Autowired
    private ReservationService reservationService;
    
    // =================== HOTELS ===================
    
    @GetMapping("/hotels")
    public ResponseEntity<List<Hotel>> getAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        return ResponseEntity.ok(hotels);
    }
    
    @GetMapping("/hotels/{id}")
    public ResponseEntity<Optional<Hotel>> getHotelById(@PathVariable Long id) {
        Optional<Hotel> hotel = hotelService.getHotelById(id);
        return hotel.isPresent() ? ResponseEntity.ok(hotel) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/hotels/search/name")
    public ResponseEntity<List<Hotel>> searchHotelsByName(@RequestParam String nom) {
        List<Hotel> hotels = hotelService.searchHotelsByName(nom);
        return ResponseEntity.ok(hotels);
    }
    
    @GetMapping("/hotels/search/city")
    public ResponseEntity<List<Hotel>> searchHotelsByCity(@RequestParam String ville) {
        List<Hotel> hotels = hotelService.searchHotelsByCity(ville);
        return ResponseEntity.ok(hotels);
    }
    
    // =================== RESERVATIONS ===================
    
    @PostMapping("/reservations")
    public ResponseEntity<Reservation> createReservation(@RequestBody Reservation reservation) {
        try {
            Reservation created = reservationService.createReservation(reservation);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }
    
    @GetMapping("/reservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = reservationService.getAllReservations();
        return ResponseEntity.ok(reservations);
    }
    
    @GetMapping("/reservations/{id}")
    public ResponseEntity<Optional<Reservation>> getReservationById(@PathVariable Long id) {
        Optional<Reservation> reservation = reservationService.getReservationById(id);
        return reservation.isPresent() ? ResponseEntity.ok(reservation) : ResponseEntity.notFound().build();
    }
    
    @GetMapping("/reservations/search/date")
    public ResponseEntity<List<Reservation>> searchByDate(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LocalDateTime dateTime = date.atStartOfDay();
        List<Reservation> reservations = reservationService.searchByDate(dateTime);
        return ResponseEntity.ok(reservations);
    }
    
    @GetMapping("/reservations/search/range")
    public ResponseEntity<List<Reservation>> searchByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime debut,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fin) {
        List<Reservation> reservations = reservationService.searchByDateRange(debut, fin);
        return ResponseEntity.ok(reservations);
    }
    
    @GetMapping("/reservations/search/hotel/{hotelId}")
    public ResponseEntity<List<Reservation>> searchByHotel(@PathVariable Long hotelId) {
        List<Reservation> reservations = reservationService.searchByHotel(hotelId);
        return ResponseEntity.ok(reservations);
    }
    
    @GetMapping("/reservations/search/client/{clientId}")
    public ResponseEntity<List<Reservation>> searchByClient(@PathVariable Long clientId) {
        List<Reservation> reservations = reservationService.searchByClient(clientId);
        return ResponseEntity.ok(reservations);
    }
    
    // =================== HEALTH CHECK ===================
    
    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("FrontOffice is running");
    }
}
