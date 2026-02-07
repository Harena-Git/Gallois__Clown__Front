package com.clown.frontoffice.service;

import com.clown.frontoffice.model.Hotel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class HotelService {
    
    private static final Logger logger = Logger.getLogger(HotelService.class.getName());
    
    @Autowired
    private RestTemplate restTemplate;
    
    @Value("${backoffice.api.url:http://localhost:8080/backoffice/api/public}")
    private String backofficeApiUrl;
    
    public List<Hotel> getAllHotels() {
        try {
            String url = backofficeApiUrl + "/hotels";
            ResponseEntity<List<Hotel>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Hotel>>() {}
            );
            return response.getBody() != null ? response.getBody() : List.of();
        } catch (RestClientException e) {
            logger.warning("Erreur lors de la récupération des hôtels: " + e.getMessage());
            return List.of();
        }
    }
    
    public List<Hotel> searchHotelsByName(String nom) {
        try {
            String url = backofficeApiUrl + "/hotels/search/name?nom=" + nom;
            ResponseEntity<List<Hotel>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Hotel>>() {}
            );
            return response.getBody() != null ? response.getBody() : List.of();
        } catch (RestClientException e) {
            logger.warning("Erreur lors de la recherche par nom: " + e.getMessage());
            return List.of();
        }
    }
    
    public List<Hotel> searchHotelsByCity(String ville) {
        try {
            String url = backofficeApiUrl + "/hotels/search/city?ville=" + ville;
            ResponseEntity<List<Hotel>> response = restTemplate.exchange(
                    url,
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Hotel>>() {}
            );
            return response.getBody() != null ? response.getBody() : List.of();
        } catch (RestClientException e) {
            logger.warning("Erreur lors de la recherche par ville: " + e.getMessage());
            return List.of();
        }
    }
    
    public Optional<Hotel> getHotelById(Long id) {
        try {
            String url = backofficeApiUrl + "/hotels/" + id;
            ResponseEntity<Hotel> response = restTemplate.getForEntity(url, Hotel.class);
            return response.getBody() != null ? Optional.of(response.getBody()) : Optional.empty();
        } catch (RestClientException e) {
            logger.warning("Erreur lors de la récupération de l'hôtel: " + e.getMessage());
            return Optional.empty();
        }
    }
    
    public List<Hotel> getAllActiveHotels() {
        return getAllHotels(); // Backend filtre les actifs
    }
}
