package com.example.miniproject.Repositories;

import com.example.miniproject.entities.ProductBacklog;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductBacklogRepo extends JpaRepository<ProductBacklog, Integer> {
}
