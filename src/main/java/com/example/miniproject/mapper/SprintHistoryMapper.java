package com.example.miniproject.mapper;

import com.example.miniproject.DTO.SprintHistoryDTO;
import com.example.miniproject.entities.SprintHistory;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SprintHistoryMapper {
    SprintHistoryMapper INSTANCE = Mappers.getMapper(SprintHistoryMapper.class);

    @Mapping(source = "sprintBacklog.id", target = "sprintBacklogId")
    SprintHistoryDTO toDTO(SprintHistory sprintHistory);

    @Mapping(source = "sprintBacklogId", target = "sprintBacklog", ignore = true)
    SprintHistory toEntity(SprintHistoryDTO sprintHistoryDTO);

    List<SprintHistoryDTO> toDTOList(List<SprintHistory> sprintHistories);
}