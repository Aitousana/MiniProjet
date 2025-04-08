package com.example.miniproject.Services;

import com.example.miniproject.DTO.ProductBacklogDTO;
import com.example.miniproject.DTO.UserStoryDTO;
import com.example.miniproject.Repositories.ProductBacklogRepo;
import com.example.miniproject.entities.ProductBacklog;
import com.example.miniproject.entities.UserStory;
import com.example.miniproject.mapper.ProductBacklogMapper;
import com.example.miniproject.mapper.UserStoryMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductBacklogServiceImpl implements ProductBacklogService {

    private final ProductBacklogRepo productBacklogRepo;
    private final UserStoryService userStoryService;

    public ProductBacklogServiceImpl(ProductBacklogRepo productBacklogRepo, UserStoryService userStoryService) {
        this.productBacklogRepo = productBacklogRepo;
        this.userStoryService = userStoryService;
    }

    @Override
    public ProductBacklogDTO addProductBacklog(ProductBacklogDTO backlogDTO) {
        ProductBacklog backlog = ProductBacklogMapper.INSTANCE.toEntity(backlogDTO);
        ProductBacklog savedBacklog = productBacklogRepo.save(backlog);
        return ProductBacklogMapper.INSTANCE.toDTO(savedBacklog);
    }

    @Override
    public ProductBacklogDTO updateProductBacklog(ProductBacklogDTO backlogDTO) {
        ProductBacklog existingBacklog = productBacklogRepo.findById(backlogDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog avec l'ID " + backlogDTO.getId() + " non trouvé"));
        existingBacklog.setTitle(backlogDTO.getTitle());
        ProductBacklog updatedBacklog = productBacklogRepo.save(existingBacklog);
        return ProductBacklogMapper.INSTANCE.toDTO(updatedBacklog);
    }

    @Override
    public void deleteProductBacklog(ProductBacklogDTO backlogDTO) {
        ProductBacklog backlog = productBacklogRepo.findById(backlogDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog avec l'ID " + backlogDTO.getId() + " non trouvé"));
        productBacklogRepo.delete(backlog);
    }

    @Override
    public List<ProductBacklogDTO> getAllProductBacklogs() {
        return productBacklogRepo.findAll().stream()
                .map(ProductBacklogMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<ProductBacklogDTO> getProductBacklogById(int id) {
        return productBacklogRepo.findById(id).map(ProductBacklogMapper.INSTANCE::toDTO);
    }

    @Override
    public ProductBacklog getProductBacklogEntityById(int id) {
        return productBacklogRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog avec l'ID " + id + " non trouvé"));
    }

//    @Override
//    public List<UserStoryDTO> prioritizeProductBacklog(int productBacklogId, String method) {
//        ProductBacklog backlog = getProductBacklogEntityById(productBacklogId);
//        List<UserStory> userStories = backlog.getUserStories();
//        switch (method) {
//            case "MoSCoW":
//                userStories.sort(Comparator.comparing(UserStory::getMoscowPriority,
//                        Comparator.comparingInt(p -> switch (p != null ? p : "") {
//                            case "Must" -> 1;
//                            case "Should" -> 2;
//                            case "Could" -> 3;
//                            case "Won't" -> 4;
//                            default -> 5;
//                        })));
//                break;
//            case "ValueVsEffort":
//                userStories.sort(Comparator.comparingDouble(us -> us.getBusinessValue() / (double) us.getComplexity()));
//                break;
//            case "WSJF":
//                userStories.sort(Comparator.comparingDouble(userStoryService::calculateWSJF).reversed());
//                break;
//            default:
//                throw new IllegalArgumentException("Méthode de priorisation non supportée : " + method);
//        }
//        return userStories.stream().map(UserStoryMapper.INSTANCE::toDTO).collect(Collectors.toList());
//    }
}