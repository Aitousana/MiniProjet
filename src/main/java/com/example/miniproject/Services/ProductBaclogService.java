package com.example.miniproject.Services;

import com.example.miniproject.entities.ProductBacklog;
import com.example.miniproject.entities.UserStory;

import java.util.List;
import java.util.Optional;

public interface ProductBaclogService {
public  ProductBacklog addProductBacklog(ProductBacklog backlog);
   public List<ProductBacklog> getAllProductBacklogs();
    public Optional<ProductBacklog> getProductBacklogById(int id);
    public ProductBacklog updateProductBacklog(int id, ProductBacklog backlog);
    public void deleteProductBacklog(int id);
    public ProductBacklog removeUserStoryFromBacklog(int backlogId, int userStoryId);
    public ProductBacklog addUserStoryToBacklog(int backlogId, UserStory userStory);

}
