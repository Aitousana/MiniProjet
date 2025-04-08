package com.example.miniproject.mapper;

import com.example.miniproject.DTO.TaskDTO;
import com.example.miniproject.entities.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    TaskMapper INSTANCE = Mappers.getMapper(TaskMapper.class);

    @Mapping(source = "userStory.id", target = "userStoryId")
    @Mapping(source = "sprintBacklog.id", target = "sprintBacklogId")
    @Mapping(source = "status", target = "status")
    TaskDTO toDTO(Task task);

    @Mapping(source = "userStoryId", target = "userStory", ignore = true)
    @Mapping(source = "sprintBacklogId", target = "sprintBacklog", ignore = true)
    @Mapping(source = "status", target = "status")
    Task toEntity(TaskDTO taskDTO);

    List<TaskDTO> toDTOList(List<Task> tasks);
}