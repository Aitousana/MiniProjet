package com.example.miniproject.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user_story")
@Getter
@Setter
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

    @Column(name = "critere_acceptation")
    private String critereAcceptation;

    @ManyToOne
    @JoinColumn(name = "epic_id")
    private Epic epic;

    @ManyToOne
    @JoinColumn(name = "product_backlog_id")
    private ProductBacklog productBacklog;

    @ManyToOne
    @JoinColumn(name = "sprint_backlog_id")
    private SprintBacklog sprintBacklog;

    @OneToMany(mappedBy = "userStory", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Task> tasks = new ArrayList<>();
    private int complexity;
    public enum Priorite {
        HAUTE, MOYENNE, BASSE
    }

    public enum Statut {
        A_FAIRE, EN_COURS, REVUE, TERMINEE
    }
    // Champs de priorisation
//    private int businessValue; // 1-10
//    private int urgency; // 1-10
    // 1-13
//    private int risk; // 1-10
//    private String dependencies; // Ex: "1,3,5"
//    private String prioritizationMethod; // "MoSCoW", "ValueVsEffort", "WSJF"
//    private String moscowPriority; // "Must", "Should", "Could", "Won't"


}