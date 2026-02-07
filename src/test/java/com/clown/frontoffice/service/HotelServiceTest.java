package com.clown.frontoffice.service;

import com.clown.frontoffice.model.Hotel;
import com.clown.frontoffice.repository.HotelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class HotelServiceTest {

    @Autowired
    private HotelService hotelService;

    @Autowired
    private HotelRepository hotelRepository;

    private Hotel testHotel;

    @BeforeEach
    public void setUp() {
        // Clean up
        hotelRepository.deleteAll();

        // Create test hotel
        testHotel = new Hotel();
        testHotel.setNom("Hotel Test");
        testHotel.setVille("Paris");
        testHotel.setActive(true);
        testHotel = hotelRepository.save(testHotel);
    }

    @Test
    public void testGetAllHotels() {
        List<Hotel> hotels = hotelService.getAllHotels();
        assertNotNull(hotels);
        assertTrue(hotels.size() >= 1);
    }

    @Test
    public void testSearchByName() {
        List<Hotel> hotels = hotelService.searchHotelsByName("Test");
        assertFalse(hotels.isEmpty());
        assertEquals(1, hotels.size());
        assertEquals("Hotel Test", hotels.get(0).getNom());
    }

    @Test
    public void testSearchByCity() {
        List<Hotel> hotels = hotelService.searchHotelsByCity("Paris");
        assertFalse(hotels.isEmpty());
        assertEquals("Paris", hotels.get(0).getVille());
    }

    @Test
    public void testGetHotelById() {
        var hotel = hotelService.getHotelById(testHotel.getId());
        assertTrue(hotel.isPresent());
        assertEquals("Hotel Test", hotel.get().getNom());
    }

    @Test
    public void testGetNonExistentHotel() {
        var hotel = hotelService.getHotelById(99999L);
        assertFalse(hotel.isPresent());
    }
}
