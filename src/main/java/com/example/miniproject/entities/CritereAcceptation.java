package com.example.miniproject.entities;

import jakarta.persistence.*;

@Entity
public class CritereAcceptation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String description;
    @Enumerated(EnumType.STRING)
    private Status status;
    public CritereAcceptation() {}
    public CritereAcceptation(String description, Status status) {
        this.description = description;
        this.status = status;
    }
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Status getStatus() {
        return status;

    }
    public void setStatus(Status status) {
        this.status = status;
    }

    public enum Status {
        VALID,NONVALID;
    }
}
