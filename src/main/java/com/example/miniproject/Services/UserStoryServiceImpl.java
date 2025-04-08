package com.example.miniproject.Services;

import com.example.miniproject.DTO.EpicDTO;
import com.example.miniproject.DTO.ProductBacklogDTO;
import com.example.miniproject.DTO.UserStoryDTO;
import com.example.miniproject.Repositories.UserStoryRepo;
import com.example.miniproject.entities.SprintBacklog;
import com.example.miniproject.entities.UserStory;
import com.example.miniproject.mapper.UserStoryMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class UserStoryServiceImpl implements UserStoryService {

    private final UserStoryRepo userStoryRepo;
    private final ProductBacklogService productBacklogService;
    private final EpicService epicService;
    private final SprintBacklogService sprintBacklogService;

    public UserStoryServiceImpl(UserStoryRepo userStoryRepo,
                                @Lazy ProductBacklogService productBacklogService,
                                @Lazy EpicService epicService,
                                @Lazy SprintBacklogService sprintBacklogService) {
        this.userStoryRepo = userStoryRepo;
        this.productBacklogService = productBacklogService;
        this.epicService = epicService;
        this.sprintBacklogService = sprintBacklogService;
    }

    @Override
    public void addUserStory(UserStoryDTO userStoryDTO) {
        Objects.requireNonNull(userStoryDTO, "UserStoryDTO ne peut pas être null");

        UserStory userStory = UserStoryMapper.INSTANCE.toEntity(userStoryDTO);

        if (userStoryDTO.getEpicId() == 0) {
            throw new EntityNotFoundException("L'Epic associé à cette User Story est requis.");
        }
        userStory.setEpic(epicService.getEpicEntityById(userStoryDTO.getEpicId()));

        if (userStoryDTO.getProductBacklogId() == 0) {
            throw new EntityNotFoundException("Le Product Backlog associé à cette User Story est requis.");
        }
        userStory.setProductBacklog(productBacklogService.getProductBacklogEntityById(userStoryDTO.getProductBacklogId()));

        if (userStoryDTO.getSprintBacklogId() != 0) {
            SprintBacklog sprint = sprintBacklogService.getSprintBacklogEntityById(userStoryDTO.getSprintBacklogId());
            int currentLoad = sprint.getUserStories().stream().mapToInt(UserStory::getComplexity).sum();
            if (currentLoad + userStoryDTO.getComplexity() > sprint.getCapacity()) {
                throw new IllegalStateException("La capacité du Sprint est dépassée");
            }
            userStory.setSprintBacklog(sprint);
        }

        String descriptionPattern = "(?i)^en tant que.*,\s*je veux.*,\s*afin.*";
        if (userStory.getDescription() == null || !userStory.getDescription().matches(descriptionPattern)) {
            throw new IllegalArgumentException("La description doit être sous la forme : 'En tant que..., je veux..., afin ...'");
        }

        String gherkinPattern = "(?i)^Given .*\\s*When .*\\s*Then .*";
        if (userStory.getCritereAcceptation() == null || !userStory.getCritereAcceptation().matches(gherkinPattern)) {
            throw new IllegalArgumentException("Le critère d'acceptation doit être sous la forme Gherkin : 'Given ..., When ..., Then ...'");
        }

//        if (userStoryDTO.getBusinessValue() < 0 || userStoryDTO.getBusinessValue() > 10) {
//            throw new IllegalArgumentException("Business Value doit être entre 0 et 10");
//        }
//        if (userStoryDTO.getUrgency() < 0 || userStoryDTO.getUrgency() > 10) {
//            throw new IllegalArgumentException("Urgency doit être entre 0 et 10");
//        }
//        if (userStoryDTO.getComplexity() < 0 || userStoryDTO.getComplexity() > 13) {
//            throw new IllegalArgumentException("Complexity doit être entre 0 et 13");
//        }
//        if (userStoryDTO.getRisk() < 0 || userStoryDTO.getRisk() > 10) {
//            throw new IllegalArgumentException("Risk doit être entre 0 et 10");
//        }
//        if (userStoryDTO.getMoscowPriority() != null &&
//                !List.of("Must", "Should", "Could", "Won't").contains(userStoryDTO.getMoscowPriority())) {
//            throw new IllegalArgumentException("Moscow Priority doit être 'Must', 'Should', 'Could', ou 'Won't'");
//        }
//        if (userStoryDTO.getPrioritizationMethod() != null &&
//                !List.of("MoSCoW", "ValueVsEffort", "WSJF").contains(userStoryDTO.getPrioritizationMethod())) {
//            throw new IllegalArgumentException("Prioritization Method doit être 'MoSCoW', 'ValueVsEffort', ou 'WSJF'");
//        }

        UserStory savedUserStory = userStoryRepo.save(userStory);

        EpicDTO epicDTO = epicService.getEpicById(savedUserStory.getEpic().getId());
        epicDTO.getUserStoryIds().add(savedUserStory.getId());
        epicService.updateEpic(epicDTO);

        ProductBacklogDTO backlogDTO = productBacklogService.getProductBacklogById(savedUserStory.getProductBacklog().getBacklogId())
                .orElseThrow(() -> new EntityNotFoundException("ProductBacklog non trouvé"));
        backlogDTO.getUserStoryIds().add(savedUserStory.getId());
        productBacklogService.updateProductBacklog(backlogDTO);
    }

    @Override
    public void deleteUserStory(UserStoryDTO userStoryDTO) {
        UserStory userStory = userStoryRepo.findById(userStoryDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("User Story avec l'ID " + userStoryDTO.getId() + " non trouvée"));
        if (userStory.getStatut() == UserStory.Statut.TERMINEE) {
            throw new IllegalStateException("Impossible de supprimer une User Story terminée");
        }
        userStoryRepo.delete(userStory);
    }

    @Override
    public void updateUserStory(UserStoryDTO userStoryDTO) {
        UserStory existingUserStory = userStoryRepo.findById(userStoryDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("User Story avec l'ID " + userStoryDTO.getId() + " non trouvée"));

        if (!Objects.equals(existingUserStory.getStatut(), UserStory.Statut.valueOf(userStoryDTO.getStatus()))) {
            if (!canMoveToStatut(existingUserStory, UserStory.Statut.valueOf(userStoryDTO.getStatus()))) {
                throw new IllegalStateException("Transition de statut non autorisée : de " + existingUserStory.getStatut() + " à " + userStoryDTO.getStatus());
            }
            existingUserStory.setStatut(UserStory.Statut.valueOf(userStoryDTO.getStatus()));
        } else if (existingUserStory.getStatut() == UserStory.Statut.A_FAIRE) {
            existingUserStory.setTitle(userStoryDTO.getTitle());
            existingUserStory.setDescription(userStoryDTO.getDescription());
            existingUserStory.setPriorite(UserStory.Priorite.valueOf(userStoryDTO.getPriorite()));
            existingUserStory.setCritereAcceptation(userStoryDTO.getAcceptanceCriteria());
            existingUserStory.setEpic(epicService.getEpicEntityById(userStoryDTO.getEpicId()));
            existingUserStory.setProductBacklog(productBacklogService.getProductBacklogEntityById(userStoryDTO.getProductBacklogId()));
            if (userStoryDTO.getSprintBacklogId() != 0) {
                existingUserStory.setSprintBacklog(sprintBacklogService.getSprintBacklogEntityById(userStoryDTO.getSprintBacklogId()));
            } else {
                existingUserStory.setSprintBacklog(null);
            }

        } else {
            throw new IllegalStateException("Seul le statut peut être modifié après l'état 'A_FAIRE'");
        }

        userStoryRepo.save(existingUserStory);
    }

    @Override
    public List<UserStoryDTO> getUserStories() {
        return userStoryRepo.findAll().stream()
                .map(UserStoryMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public Page<UserStoryDTO> getUserStories(Pageable pageable) {
        return userStoryRepo.findAll(pageable).map(UserStoryMapper.INSTANCE::toDTO);
    }

    @Override
    public UserStoryDTO getUserStoryById(int id) {
        UserStory userStory = userStoryRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User Story avec l'ID " + id + " non trouvée"));
        return UserStoryMapper.INSTANCE.toDTO(userStory);
    }

    @Override
    public void deleteAllUserStories(List<UserStoryDTO> userStoryDTOList) {
        if (userStoryDTOList == null || userStoryDTOList.isEmpty()) {
            throw new IllegalArgumentException("La liste des User Stories à supprimer ne peut pas être vide");
        }
        userStoryDTOList.forEach(this::deleteUserStory);
    }

    @Override
    public List<UserStoryDTO> getUserStoriesByEpicId(int epicId) {
        return userStoryRepo.findByEpicId(epicId).stream()
                .map(UserStoryMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public UserStory getUserStoryEntityById(int id) {
        return userStoryRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User Story avec l'ID " + id + " non trouvée"));
    }



    private boolean canMoveToStatut(UserStory userStory, UserStory.Statut newStatut) {
        if (userStory.getStatut() == UserStory.Statut.TERMINEE) {
            return false;
        }
        switch (userStory.getStatut()) {
            case A_FAIRE: return newStatut == UserStory.Statut.EN_COURS;
            case EN_COURS: return newStatut == UserStory.Statut.REVUE;
            case REVUE: return newStatut == UserStory.Statut.TERMINEE;
            default: return false;
        }
    }
}