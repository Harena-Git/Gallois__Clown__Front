package com.clown.frontoffice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "id_client")
    private Long idClient;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_hotel", nullable = false)
    private Hotel hotel;
    
    @Column(name = "nombre_passager", nullable = false)
    private Integer nombrePassager;
    
    @Column(name = "date_heure_arrive", nullable = false)
    private LocalDateTime dateHeureArrive;
    
    @Column(name = "date_heure_depart")
    private LocalDateTime dateHeureDepart;
    
    @Column(name = "statut", length = 50)
    private String statut = "EN_ATTENTE";
    
    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
    
    @Column(name = "date_creation")
    private LocalDateTime dateCreation = LocalDateTime.now();
    
    @Column(name = "date_modification")
    private LocalDateTime dateModification;
}
