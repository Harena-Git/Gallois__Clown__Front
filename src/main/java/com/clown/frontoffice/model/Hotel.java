package com.clown.frontoffice.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "hotel")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Hotel {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    
    @Column(name = "nom", nullable = false, length = 255)
    private String nom;
    
    @Column(name = "description", length = 500)
    private String description;
    
    @Column(name = "adresse", length = 255)
    private String adresse;
    
    @Column(name = "ville", length = 100)
    private String ville;
    
    @Column(name = "code_postal", length = 10)
    private String codePostal;
    
    @Column(name = "telephone", length = 20)
    private String telephone;
    
    @Column(name = "email", length = 100)
    private String email;
    
    @Column(name = "site_web", length = 255)
    private String siteWeb;
    
    @Column(name = "nombre_chambres")
    private Integer nombreChambres;
    
    @Column(name = "prix_moyen_nuit")
    private Double prixMoyenNuit;
    
    @Column(name = "active")
    private Boolean active = true;
}
