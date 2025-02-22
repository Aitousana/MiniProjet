package com.example.miniproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.io.Serializable;

@Entity
@Table(name = "user_story")
public class UserStory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private Priorite priorite;

    @Enumerated(EnumType.STRING)
    private Statut statut;

    // Constructeur sans arguments (nécessaire pour JPA)
    public UserStory() {
    }

    // Constructeur avec tous les paramètres
    public UserStory(int id, String title, String description, Priorite priorite, Statut statut) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priorite = priorite;
        this.statut = statut;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priorite getPriorite() {
        return priorite;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }

    public Statut getStatut() {
        return statut;
    }

    public void setStatut(Statut statut) {
        this.statut = statut;
    }

    // Enum Priorite
    public enum Priorite {
        HAUTE, MOYENNE, BASSE;
    }

    // Enum Statut
    public enum Statut {
        A_FAIRE, EN_COURS, REVUE, TERMINEE;
    }
}
