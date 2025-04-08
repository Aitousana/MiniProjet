package com.example.miniproject.mapper;

import com.example.miniproject.DTO.ProductBacklogDTO;
import com.example.miniproject.entities.Epic;
import com.example.miniproject.entities.ProductBacklog;
import com.example.miniproject.entities.UserStory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductBacklogMapper {
    ProductBacklogMapper INSTANCE = Mappers.getMapper(ProductBacklogMapper.class);

    @Mapping(source = "backlogId", target = "id")
    @Mapping(source = "epics", target = "epicIds", qualifiedByName = "mapEpicsToIds")
    @Mapping(source = "userStories", target = "userStoryIds", qualifiedByName = "mapUserStoriesToIds")
    ProductBacklogDTO toDTO(ProductBacklog productBacklog);

    @Mapping(source = "id", target = "backlogId")
    @Mapping(source = "epicIds", target = "epics", ignore = true)
    @Mapping(source = "userStoryIds", target = "userStories", ignore = true)
    ProductBacklog toEntity(ProductBacklogDTO productBacklogDTO);

    List<ProductBacklogDTO> toDTOList(List<ProductBacklog> productBacklogs);

    @org.mapstruct.Named("mapEpicsToIds")
    default List<Integer> mapEpicsToIds(List<Epic> epics) {
        return epics != null ? epics.stream().map(Epic::getId).toList() : null;
    }

    @org.mapstruct.Named("mapUserStoriesToIds")
    default List<Integer> mapUserStoriesToIds(List<UserStory> userStories) {
        return userStories != null ? userStories.stream().map(UserStory::getId).toList() : null;
    }
}