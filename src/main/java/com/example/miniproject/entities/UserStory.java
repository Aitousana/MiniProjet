package com.example.miniproject.entities;

import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.io.Serializable;

@Entity
@Table(name = "user_story")
public class UserStory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull
    private String title;
    private String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Priorite priorite;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Statut statut;

    @ManyToOne(optional = false)
    @JoinColumn(name = "epic_id")
    private Epic epic;
    @NotNull
    private String Critere_Acceptation;


    public UserStory() {
    }

    // Constructeur avec tous les paramètres
    public UserStory(int id, String title, String description, Priorite priorite, Statut statut, Epic epic, String Critere_Acceptation) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priorite = priorite;
        this.statut = statut;
        this.epic = epic;
        this.Critere_Acceptation = Critere_Acceptation;
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

    public Epic getEpic() {
        return epic;
    }

    public void setEpic(Epic epic) {
        this.epic = epic;
    }
    public String getCritere_Acceptation() {
        return Critere_Acceptation;
    }
    public void setCritere_Acceptation(String Critere_Acceptation) {
        this.Critere_Acceptation = Critere_Acceptation;
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
