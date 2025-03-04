package com.example.miniproject.entities;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "epic")
public class Epic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    @OneToMany(mappedBy = "epic", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserStory> userStories;
    @ManyToOne
    @JoinColumn(name = "ProductBacklog")
    private ProductBacklog productBacklog;

    // Constructeur par défaut
    public Epic() {
    }

    // Constructeur avec paramètres
    public Epic(int id, String title, List<UserStory> userStories) {
        this.id = id;
        this.title = title;
        this.userStories = userStories;
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

    public List<UserStory> getUserStories() {
        return userStories;
    }

    public void setUserStories(List<UserStory> userStories) {
        this.userStories = userStories;
    }
    public ProductBacklog getProductBacklog() {
        return productBacklog;
    }
    public void setProductBacklog(ProductBacklog productBacklog) {
        this.productBacklog = productBacklog;
    }
}
