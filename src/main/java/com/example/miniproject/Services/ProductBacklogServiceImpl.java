package com.example.miniproject.Services;

import com.example.miniproject.Repositories.ProductBacklogRepo;
import com.example.miniproject.entities.ProductBacklog;
import com.example.miniproject.entities.UserStory;
import com.example.miniproject.entities.Epic;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductBacklogServiceImpl implements ProductBaclogService {

    private final ProductBacklogRepo productBacklogRepo;
    private final UserStoryService userStoryService;
    private final EpicService epicService;

    public ProductBacklogServiceImpl(ProductBacklogRepo productBacklogRepo, UserStoryService userStoryService, EpicService epicService) {
        this.productBacklogRepo = productBacklogRepo;
        this.userStoryService = userStoryService;
        this.epicService = epicService;
    }

    @Override
    public ProductBacklog addProductBacklog(ProductBacklog backlog) {

        return productBacklogRepo.save(backlog);

    }

    @Override
    public List<ProductBacklog> getAllProductBacklogs() {

        return productBacklogRepo.findAll();
    }

    @Override
    public Optional<ProductBacklog> getProductBacklogById(int id) {

        return productBacklogRepo.findById(id);
    }

    @Override
    public ProductBacklog updateProductBacklog(ProductBacklog backlog) {
        if (!productBacklogRepo.existsById(backlog.getBacklogId())) {
            throw new EntityNotFoundException("ProductBacklog with id " +backlog.getBacklogId() + " not found");
        }
        backlog.setTitle(backlog.getTitle());
        backlog.setUserStories(backlog.getUserStories());
        backlog.setEpics(backlog.getEpics());
        return productBacklogRepo.save(backlog);
    }

    @Override
    public void deleteProductBacklog(ProductBacklog backlog) {
        if (!productBacklogRepo.existsById(backlog.getBacklogId())) {
            throw new EntityNotFoundException("ProductBacklog with id " + backlog.getBacklogId() + " not found");
        }

        List<Epic> epics = backlog.getEpics();
        epicService.deleteAllEpic(epics);
        productBacklogRepo.deleteById(backlog.getBacklogId());

    }


}