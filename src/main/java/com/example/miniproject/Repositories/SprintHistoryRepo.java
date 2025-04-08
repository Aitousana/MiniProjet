package com.example.miniproject.Repositories;

import com.example.miniproject.entities.SprintHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SprintHistoryRepo extends JpaRepository<SprintHistory, Integer> {
    List<SprintHistory> findBySprintBacklogId(int sprintBacklogId);
}