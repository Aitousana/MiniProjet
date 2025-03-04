package com.example.miniproject.Services;

import com.example.miniproject.Repositories.UserStoryRepo;
import com.example.miniproject.entities.UserStory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStoryServiceImpl implements UserStoryService {
    private final UserStoryRepo userStoryRepo;
    private final ProductBaclogService productBaclogService;
    private final UserStoryService userStoryService;
    private final EpicService epicService;

    public UserStoryServiceImpl(UserStoryRepo userStoryRepo, ProductBaclogService productBaclogService, UserStoryService userStoryService, EpicService epicService) {
        this.userStoryRepo = userStoryRepo;
        this.productBaclogService=productBaclogService;
        this.userStoryService = userStoryService;
        this.epicService = epicService;
    }

    @Override
    public void addUserStory(UserStory userStory) {
        if (userStory.getEpic() == null) {
            throw new EntityNotFoundException("L'Epic associé à cette User Story n'existe pas.");
        }
        if (userStory.getProductBacklog()== null) {
            throw new EntityNotFoundException("Product Backlog associé à cette User Story n'existe pas.");
        }

        // Vérification du format de la description
        String descriptionPattern = "(?i)^en tant que.*,\s*je veux.*,\s*afin.*";
        if (userStory.getDescription() == null || !userStory.getDescription().matches(descriptionPattern)) {
            throw new IllegalArgumentException("La description doit être sous la forme : 'En tant que..., je veux..., afin ...'");
        }

        // Vérification du format du critère d'acceptation
        String gherkinPattern = "(?i)^Given .*\\s*When .*\\s*Then .*";
        if (userStory.getCritere_Acceptation() == null || !userStory.getCritere_Acceptation().matches(gherkinPattern)) {
            throw new IllegalArgumentException("Le critère d'acceptation doit être sous la forme Gherkin complète : 'Given ..., When ..., Then ...'");
        }

        userStoryRepo.save(userStory);
        epicService.UpdateEpic(userStory.getEpic());
        productBaclogService.updateProductBacklog(userStory.getProductBacklog());

    }


    @Override
    public void deleteUserStory(UserStory userStory) {
        if (!userStoryRepo.existsById(userStory.getId())){
            throw new EntityNotFoundException("UserStory with id " + userStory.getId() + " not found");
        }
        if(userStory.getStatut()== UserStory.Statut.TERMINEE){
            throw new IllegalStateException("Impossible de Supprimer une User Story  à l'état 'Terminé'.");
        }
        userStoryRepo.deleteById(userStory.getId());


    }

    public boolean canMoveToStatut(UserStory userStory, UserStory.Statut newStatut) {

        if (userStory.getStatut() == UserStory.Statut.TERMINEE) {
            return false;
        }

        // Vérifie que le statut change de manière logique : "A_FAIRE" -> "EN_COURS" -> "REVUE" -> "TERMINEE"
        switch (userStory.getStatut()) {
            case A_FAIRE:
                return newStatut == UserStory.Statut.EN_COURS;
            case EN_COURS:
                return newStatut == UserStory.Statut.REVUE;
            case REVUE:
                return newStatut == UserStory.Statut.TERMINEE;
            default:
                return false;
        }
    }


    @Override
    public void updateUserStory(UserStory userStory) {
        // Récupérer la User Story existante à partir de la base de données
        UserStory existingUserStory = userStoryRepo.findById(userStory.getId())
                .orElseThrow(() -> new EntityNotFoundException("Aucune User Story trouvée avec cet ID."));

        // Vérifier si le statut est modifié
        if (existingUserStory.getStatut() != userStory.getStatut()) {

                if (!canMoveToStatut(existingUserStory, userStory.getStatut())) {
                    throw new IllegalStateException("Transition de statut non autorisée.");

            }
            existingUserStory.setStatut(userStory.getStatut());
        } else {
            // Si la User Story est "A_FAIRE", on peut modifier d'autres champs
            if (existingUserStory.getStatut() == UserStory.Statut.A_FAIRE) {
                // Vérification du format de la description
                String descriptionPattern = "(?i)^en tant que.*,\s*je veux.*,\s*afin.*";
                if (userStory.getDescription() == null || !userStory.getDescription().matches(descriptionPattern)) {
                    throw new IllegalArgumentException("La description doit être sous la forme : 'En tant que..., je veux..., afin ...'");
                }

                // Vérification du format du critère d'acceptation
                String gherkinPattern = "(?i)^Given .*\\s*When .*\\s*Then .*";
                if (userStory.getCritere_Acceptation() == null || !userStory.getCritere_Acceptation().matches(gherkinPattern)) {
                    throw new IllegalArgumentException("Le critère d'acceptation doit être sous la forme Gherkin complète : 'Given ..., When ..., Then ...'");
                }

                existingUserStory.setTitle(userStory.getTitle());
                existingUserStory.setDescription(userStory.getDescription());
                existingUserStory.setPriorite(userStory.getPriorite());
                existingUserStory.setEpic(userStory.getEpic());
            } else {
                throw new IllegalStateException("Seul le statut peut être modifié après l'état 'A_FAIRE'.");
            }
        }

        userStoryRepo.save(existingUserStory);
        epicService.UpdateEpic(userStory.getEpic());
        productBaclogService.updateProductBacklog(userStory.getProductBacklog());
    }

    @Override
    public List<UserStory> getUserStory() {
        return userStoryRepo.findAll();
    }

    @Override
    public UserStory getUserStoryById(int id) {
        return userStoryRepo.findById(id).orElse(null);
    }
      @Override
      public void delateAllUserStory(List<UserStory> userStoryList){
        if(userStoryList.size()==0){
            throw new NullPointerException("aucun UserStory Stockée");
        }
        for(UserStory userStory : userStoryList){
            userStoryService.deleteUserStory(userStory);
        }


      }

}


