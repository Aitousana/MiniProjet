package com.example.miniproject.Repositories;

import com.example.miniproject.entities.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStoryRepo extends JpaRepository<UserStory, Integer> {

    List<UserStory> findByEpicId(int epicId);
}

