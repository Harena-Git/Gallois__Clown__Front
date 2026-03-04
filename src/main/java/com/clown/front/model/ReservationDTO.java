package com.clown.front.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;

public class ReservationDTO {
    private int id;
    private String idClient;
    private int nombrePassager;

    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dateHeureArrive;
    private int idHotel;

    public ReservationDTO() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
        this.idClient = idClient;
    }

    public int getNombrePassager() {
        return nombrePassager;
    }

    public void setNombrePassager(int nombrePassager) {
        this.nombrePassager = nombrePassager;
    }

    public LocalDateTime getDateHeureArrive() {
        return dateHeureArrive;
    }

    public void setDateHeureArrive(LocalDateTime dateHeureArrive) {
        this.dateHeureArrive = dateHeureArrive;
    }

    public int getIdHotel() {
        return idHotel;
    }

    public void setIdHotel(int idHotel) {
        this.idHotel = idHotel;
    }

    private String nomHotel;

    public String getNomHotel() {
        return nomHotel;
    }

    public void setNomHotel(String nomHotel) {
        this.nomHotel = nomHotel;
    }
}
