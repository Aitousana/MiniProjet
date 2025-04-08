package com.example.miniproject.mapper;

import com.example.miniproject.DTO.SprintBacklogDTO;
import com.example.miniproject.entities.SprintBacklog;
import com.example.miniproject.entities.Task;
import com.example.miniproject.entities.UserStory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SprintBacklogMapper {
    SprintBacklogMapper INSTANCE = Mappers.getMapper(SprintBacklogMapper.class);

    @Mapping(source = "userStories", target = "userStoryIds", qualifiedByName = "mapUserStoriesToIds")
    @Mapping(source = "tasks", target = "taskIds", qualifiedByName = "mapTasksToIds")
    SprintBacklogDTO toDTO(SprintBacklog sprintBacklog);

    @Mapping(source = "userStoryIds", target = "userStories", ignore = true)
    @Mapping(source = "taskIds", target = "tasks", ignore = true)
    SprintBacklog toEntity(SprintBacklogDTO sprintBacklogDTO);

    List<SprintBacklogDTO> toDTOList(List<SprintBacklog> sprintBacklogs);

    @org.mapstruct.Named("mapUserStoriesToIds")
    default List<Integer> mapUserStoriesToIds(List<UserStory> userStories) {
        return userStories != null ? userStories.stream().map(UserStory::getId).toList() : null;
    }

    @org.mapstruct.Named("mapTasksToIds")
    default List<Integer> mapTasksToIds(List<Task> tasks) {
        return tasks != null ? tasks.stream().map(Task::getId).toList() : null;
    }
}