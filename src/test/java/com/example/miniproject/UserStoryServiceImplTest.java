//package com.example.miniproject;
//
//import com.example.miniproject.Repositories.UserStoryRepo;
//import com.example.miniproject.Services.UserStoryServiceImpl;
//import com.example.miniproject.entities.UserStory;
//import jakarta.persistence.EntityNotFoundException;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import java.util.Optional;
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//class UserStoryServiceImplTest {
//
//    @Mock
//    private UserStoryRepo userStoryRepo;
//
//    @InjectMocks
//    private UserStoryServiceImpl userStoryService;
//
//    private UserStory userStory;
//
//    @BeforeEach
//    void setUp() {
//        userStory = new UserStory();
//        userStory.setId(1);
//        userStory.setTitle("Test Story");
//        userStory.setDescription("En tant que développeur, je veux écrire des tests, afin d'assurer la qualité du code.");
//        userStory.setCritere_Acceptation("Given un développeur When il exécute les tests Then ils doivent passer.");
//        userStory.setStatut(UserStory.Statut.A_FAIRE);
//    }
//
//    @Test
//    void shouldThrowExceptionWhenEpicIsNull() {
//        userStory.setEpic(null);
//        Exception exception = assertThrows(EntityNotFoundException.class, () -> userStoryService.addUserStory(userStory));
//        assertEquals("L'Epic associé à cette User Story n'existe pas.", exception.getMessage());
//    }
//
//    @Test
//    void shouldThrowExceptionWhenDescriptionIsInvalid() {
//        userStory.setDescription("Mauvais format de description");
//        Exception exception = assertThrows(EntityNotFoundException.class, () -> userStoryService.addUserStory(userStory));
//        assertEquals("La description doit être sous la forme : 'En tant que..., je veux..., afin ...'", exception.getMessage());
//    }
//
//    @Test
//    void shouldThrowExceptionWhenAcceptanceCriteriaIsInvalid() {
//        userStory.setCritere_Acceptation("Format incorrect");
//        Exception exception = assertThrows(EntityNotFoundException.class, () -> userStoryService.addUserStory(userStory));
//        assertEquals("Le critère d'acceptation doit être sous la forme Gherkin complète : 'Given ..., When ..., Then ...'", exception.getMessage());
//    }
//
//    @Test
//    void shouldDeleteUserStoryWhenValid() {
//        when(userStoryRepo.existsById(userStory.getId())).thenReturn(true);
//        when(userStoryRepo.findById(userStory.getId())).thenReturn(Optional.of(userStory));
//        userStoryService.deleteUserStory(userStory);
//        verify(userStoryRepo, times(1)).deleteById(userStory.getId());
//    }
//
//    @Test
//    void shouldNotDeleteIfUserStoryIsTerminee() {
//        userStory.setStatut(UserStory.Statut.TERMINEE);
//        Exception exception = assertThrows(EntityNotFoundException.class, () -> userStoryService.deleteUserStory(userStory));
//        assertEquals("Impossible de Supprimer une User Story  à l'état 'Terminé'.", exception.getMessage());
//    }
//
//    @Test
//    void shouldThrowExceptionIfUserStoryNotFoundOnDelete() {
//        when(userStoryRepo.existsById(userStory.getId())).thenReturn(false);
//        Exception exception = assertThrows(EntityNotFoundException.class, () -> userStoryService.deleteUserStory(userStory));
//        assertEquals("UserStory with id 1 not found", exception.getMessage());
//    }
//
//    @Test
//    void shouldUpdateUserStoryWhenValid() {
//        when(userStoryRepo.findById(userStory.getId())).thenReturn(Optional.of(userStory));
//        userStory.setDescription("En tant que testeur, je veux valider les fonctionnalités, afin de garantir la fiabilité.");
//        userStoryService.updateUserStory(userStory);
//        verify(userStoryRepo, times(1)).save(userStory);
//    }
//}
