package com.example.miniproject.Services;

import com.example.miniproject.DTO.ProductBacklogDTO;
import com.example.miniproject.DTO.UserStoryDTO;
import com.example.miniproject.entities.ProductBacklog;

import java.util.List;
import java.util.Optional;

public interface ProductBacklogService {
    ProductBacklogDTO addProductBacklog(ProductBacklogDTO backlogDTO);
    ProductBacklogDTO updateProductBacklog(ProductBacklogDTO backlogDTO);
    void deleteProductBacklog(ProductBacklogDTO backlogDTO);
    List<ProductBacklogDTO> getAllProductBacklogs();
    Optional<ProductBacklogDTO> getProductBacklogById(int id);
    ProductBacklog getProductBacklogEntityById(int id);
    //List<UserStoryDTO> prioritizeProductBacklog(int productBacklogId, String method);
}