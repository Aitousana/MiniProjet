package com.example.miniproject.Services;

import com.example.miniproject.entities.Epic;
import com.example.miniproject.entities.ProductBacklog;
import com.example.miniproject.entities.UserStory;

import java.util.List;
import java.util.Optional;

public interface ProductBaclogService {
public  ProductBacklog addProductBacklog(ProductBacklog backlog);
   public List<ProductBacklog> getAllProductBacklogs();
    public Optional<ProductBacklog> getProductBacklogById(int id);
 public ProductBacklog updateProductBacklog(ProductBacklog backlog);
 public void deleteProductBacklog(ProductBacklog backlog);




}
