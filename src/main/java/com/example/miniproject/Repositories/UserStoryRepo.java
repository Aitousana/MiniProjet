package com.example.miniproject.Repositories;

import com.example.miniproject.entities.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStoryRepo extends JpaRepository<UserStory, Integer> {
}
