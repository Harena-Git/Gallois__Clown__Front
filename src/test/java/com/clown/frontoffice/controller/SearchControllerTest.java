package com.clown.frontoffice.controller;

import com.clown.frontoffice.model.Hotel;
import com.clown.frontoffice.model.Reservation;
import com.clown.frontoffice.service.HotelService;
import com.clown.frontoffice.service.ReservationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class SearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private HotelService hotelService;

    @Autowired
    private ReservationService reservationService;

    private Hotel testHotel;

    @BeforeEach
    public void setUp() {
        // Créer un hôtel de test
        testHotel = new Hotel();
        testHotel.setNom("Hotel Test");
        testHotel.setVille("Paris");
        testHotel.setActive(true);
    }

    @Test
    public void testGetAllHotels() throws Exception {
        mockMvc.perform(get("/api/public/hotels")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(0))));
    }

    @Test
    public void testSearchHotelsByName() throws Exception {
        mockMvc.perform(get("/api/public/hotels/search/name")
                .param("nom", "Hotel")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testSearchHotelsByCity() throws Exception {
        mockMvc.perform(get("/api/public/hotels/search/city")
                .param("ville", "Paris")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateReservation() throws Exception {
        Reservation reservation = new Reservation();
        reservation.setHotel(testHotel);
        reservation.setDateHeureArrive(LocalDateTime.now().plusDays(1));
        reservation.setNombrePassager(2);

        mockMvc.perform(post("/api/public/reservations")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reservation)))
                .andExpect(status().isOk());
    }

    @Test
    public void testHealthCheck() throws Exception {
        mockMvc.perform(get("/api/public/health")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString("running")));
    }
}
