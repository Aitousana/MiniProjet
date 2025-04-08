package com.example.miniproject.Repositories;

import com.example.miniproject.entities.SprintBacklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintBacklogRepo extends JpaRepository<SprintBacklog, Integer> {
}