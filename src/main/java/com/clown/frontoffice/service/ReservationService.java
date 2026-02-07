package com.clown.frontoffice.service;

import com.clown.frontoffice.model.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class ReservationService {
    
    private static final Logger logger = Logger.getLogger(ReservationService.class.getName());
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${backoffice.api.url:http://localhost:8080/backoffice/api/public}")
    private String backofficeApiUrl;
    
    public Reservation createReservation(Reservation reservation) {
        try {
            String url = backofficeApiUrl + "/reservations";
            HttpEntity<Reservation> request = new HttpEntity<>(reservation);
            ResponseEntity<Reservation> response = restTemplate.postForEntity(url, request, Reservation.class);
            return response.getBody();
        } catch (RestClientException e) {
            logger.warning("Erreur lors de la création de la réservation: " + e.getMessage());
            throw new RuntimeException("Erreur lors de la création de la réservation", e);
        }
    }
    
    public List<Reservation> getAllReservations() {
        try {
            String url = backofficeApiUrl + "/reservations";
            ResponseEntity<List<Reservation>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Reservation>>() {}
            );
            return response.getBody() != null ? response.getBody() : List.of();
        } catch (RestClientException e) {
            logger.warning("Erreur lors de la récupération des réservations: " + e.getMessage());
            return List.of();
        }
    }
    
    public Optional<Reservation> getReservationById(Long id) {
        try {
            String url = backofficeApiUrl + "/reservations/" + id;
            ResponseEntity<Reservation> response = restTemplate.getForEntity(url, Reservation.class);
            return response.getBody() != null ? Optional.of(response.getBody()) : Optional.empty();
        } catch (RestClientException e) {
            logger.warning("Erreur lors de la récupération de la réservation: " + e.getMessage());
            return Optional.empty();
        }
    }
    
    public List<Reservation> searchByDate(LocalDateTime date) {
        try {
            String url = backofficeApiUrl + "/reservations/search/date?date=" + date.toLocalDate();
            ResponseEntity<List<Reservation>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Reservation>>() {}
            );
            return response.getBody() != null ? response.getBody() : List.of();
        } catch (RestClientException e) {
            logger.warning("Erreur lors de la recherche par date: " + e.getMessage());
            return List.of();
        }
    }
    
    public List<Reservation> searchByDateRange(LocalDateTime debut, LocalDateTime fin) {
        try {
            String url = backofficeApiUrl + "/reservations/search/range?debut=" + debut + "&fin=" + fin;
            ResponseEntity<List<Reservation>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Reservation>>() {}
            );
            return response.getBody() != null ? response.getBody() : List.of();
        } catch (RestClientException e) {
            logger.warning("Erreur lors de la recherche par plage: " + e.getMessage());
            return List.of();
        }
    }
    
    public List<Reservation> searchByHotel(Long hotelId) {
        try {
            String url = backofficeApiUrl + "/reservations/search/hotel/" + hotelId;
            ResponseEntity<List<Reservation>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Reservation>>() {}
            );
            return response.getBody() != null ? response.getBody() : List.of();
        } catch (RestClientException e) {
            logger.warning("Erreur lors de la recherche par hôtel: " + e.getMessage());
            return List.of();
        }
    }
    
    public List<Reservation> searchByClient(Long idClient) {
        try {
            String url = backofficeApiUrl + "/reservations/search/client/" + idClient;
            ResponseEntity<List<Reservation>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Reservation>>() {}
            );
            return response.getBody() != null ? response.getBody() : List.of();
        } catch (RestClientException e) {
            logger.warning("Erreur lors de la recherche par client: " + e.getMessage());
            return List.of();
        }
    }
    
    public List<Reservation> searchByStatut(String statut) {
        try {
            String url = backofficeApiUrl + "/reservations/search/statut?statut=" + statut;
            ResponseEntity<List<Reservation>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Reservation>>() {}
            );
            return response.getBody() != null ? response.getBody() : List.of();
        } catch (RestClientException e) {
            logger.warning("Erreur lors de la recherche par statut: " + e.getMessage());
            return List.of();
        }
    }
    
    public Reservation updateReservation(Long id, Reservation reservationDetails) {
        try {
            String url = backofficeApiUrl + "/reservations/" + id;
            HttpEntity<Reservation> request = new HttpEntity<>(reservationDetails);
            ResponseEntity<Reservation> response = restTemplate.exchange(url, HttpMethod.PUT, request, Reservation.class);
            return response.getBody();
        } catch (RestClientException e) {
            logger.warning("Erreur lors de la mise à jour: " + e.getMessage());
            return null;
        }
    }
    
    public boolean deleteReservation(Long id) {
        try {
            String url = backofficeApiUrl + "/reservations/" + id;
            restTemplate.delete(url);
            return true;
        } catch (RestClientException e) {
            logger.warning("Erreur lors de la suppression: " + e.getMessage());
            return false;
        }
    }
}
