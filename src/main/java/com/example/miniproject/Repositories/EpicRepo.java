package com.example.miniproject.Repositories;

import com.example.miniproject.entities.Epic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpicRepo extends JpaRepository<Epic, Integer> {
}
