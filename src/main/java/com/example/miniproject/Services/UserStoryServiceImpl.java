package com.example.miniproject.Services;

import com.example.miniproject.Repositories.UserStoryRepo;
import com.example.miniproject.entities.UserStory;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserStoryServiceImpl implements UserStoryService {
    private final UserStoryRepo userStoryRepo;

    public UserStoryServiceImpl(UserStoryRepo userStoryRepo) {
        this.userStoryRepo = userStoryRepo;
    }

    @Override
    public void addUserStory(UserStory userStory) {
        if (userStory.getEpic() == null){
            throw new EntityNotFoundException("L'Epic associé à cette User Story n'existe pas.");
        }
        userStoryRepo.save(userStory);
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

        // Vérifie que le changement de statut est valide
        if (existingUserStory.getStatut() != userStory.getStatut()) {
            // Si la User Story n'est pas à l'état "A_FAIRE", on vérifie si la transition est valide
            if (existingUserStory.getStatut() != UserStory.Statut.A_FAIRE) {
                if (!canMoveToStatut(existingUserStory, userStory.getStatut())) {
                    throw new IllegalStateException("Transition de statut non autorisée.");
                }
            }
            // Modifie uniquement le statut si la transition est valide
            existingUserStory.setStatut(userStory.getStatut());

        } else {
            // Si la User Story est "A_FAIRE", on peut modifier d'autres champs
            if (existingUserStory.getStatut() == UserStory.Statut.A_FAIRE) {
                existingUserStory.setTitle(userStory.getTitle());
                existingUserStory.setDescription(userStory.getDescription());
                existingUserStory.setPriorite(userStory.getPriorite());
                existingUserStory.setEpic(userStory.getEpic());
            } else {
                throw new IllegalStateException("Seul le statut peut être modifié après l'état 'A_FAIRE'.");
            }
        }


        userStoryRepo.save(existingUserStory);
    }

}


