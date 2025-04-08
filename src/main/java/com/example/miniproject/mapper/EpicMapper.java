package com.example.miniproject.mapper;

import com.example.miniproject.DTO.EpicDTO;
import com.example.miniproject.entities.Epic;
import com.example.miniproject.entities.UserStory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EpicMapper {
    EpicMapper INSTANCE = Mappers.getMapper(EpicMapper.class);

    @Mapping(source = "productBacklog.backlogId", target = "productBacklogId")
    @Mapping(source = "userStories", target = "userStoryIds", qualifiedByName = "mapUserStoriesToIds")
    EpicDTO toDTO(Epic epic);

    @Mapping(source = "productBacklogId", target = "productBacklog", ignore = true)
    @Mapping(source = "userStoryIds", target = "userStories", ignore = true)
    Epic toEntity(EpicDTO epicDTO);

    List<EpicDTO> toDTOList(List<Epic> epics);

    @org.mapstruct.Named("mapUserStoriesToIds")
    default List<Integer> mapUserStoriesToIds(List<UserStory> userStories) {
        return userStories != null ? userStories.stream().map(UserStory::getId).toList() : null;
    }
}