package com.example.miniproject.mapper;

import com.example.miniproject.DTO.UserStoryDTO;
import com.example.miniproject.entities.Task;
import com.example.miniproject.entities.UserStory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserStoryMapper {
    UserStoryMapper INSTANCE = Mappers.getMapper(UserStoryMapper.class);

    @Mapping(source = "epic.id", target = "epicId")
    @Mapping(source = "productBacklog.backlogId", target = "productBacklogId")
    @Mapping(source = "sprintBacklog.id", target = "sprintBacklogId")
    @Mapping(source = "tasks", target = "taskIds", qualifiedByName = "mapTasksToIds")
    @Mapping(source = "critereAcceptation", target = "acceptanceCriteria")
    @Mapping(source = "statut", target = "status")
    @Mapping(source = "priorite", target = "priorite")
    UserStoryDTO toDTO(UserStory userStory);

    @Mapping(source = "epicId", target = "epic", ignore = true)
    @Mapping(source = "productBacklogId", target = "productBacklog", ignore = true)
    @Mapping(source = "sprintBacklogId", target = "sprintBacklog", ignore = true)
    @Mapping(source = "taskIds", target = "tasks", ignore = true)
    @Mapping(source = "acceptanceCriteria", target = "critereAcceptation")
    @Mapping(source = "status", target = "statut")
    @Mapping(source = "priorite", target = "priorite")
    UserStory toEntity(UserStoryDTO userStoryDTO);

    List<UserStoryDTO> toDTOList(List<UserStory> userStories);

    @org.mapstruct.Named("mapTasksToIds")
    default List<Integer> mapTasksToIds(List<Task> tasks) {
        return tasks != null ? tasks.stream().map(Task::getId).toList() : null;
    }
}