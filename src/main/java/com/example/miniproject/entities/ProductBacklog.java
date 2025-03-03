package com.example.miniproject.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.List;

@Entity
@Table(name = "Product_Backlog")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductBacklog {
    @Id
    private int BacklogId;
    @NonNull
    private String title;
    @OneToMany(mappedBy ="ProductBacklog")
    private List<UserStory> userStories;
    @OneToMany(mappedBy ="ProductBacklog")
    private List<Epic> epics;

}
