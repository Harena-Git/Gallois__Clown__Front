package com.clown.front.service;

import com.clown.front.model.ApiResponse;
import com.clown.front.model.ReservationDTO;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Service
public class ReservationService {

    private final String BACKOFFICE_API_URL = "http://localhost:8080/backoffice/api/reservations";
    private final RestTemplate restTemplate = new RestTemplate();

    public List<ReservationDTO> getReservations(String date) {
        String url = BACKOFFICE_API_URL;
        if (date != null && !date.isBlank()) {
            url += "?date=" + date;
        }

        ResponseEntity<ApiResponse<List<ReservationDTO>>> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<ApiResponse<List<ReservationDTO>>>() {
                });

        ApiResponse<List<ReservationDTO>> body = response.getBody();
        return body != null ? body.getData() : java.util.Collections.emptyList();
    }
}
