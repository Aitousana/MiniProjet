package com.example.miniproject.Services;

import com.example.miniproject.DTO.EpicDTO;
import com.example.miniproject.entities.Epic;

import java.util.List;

public interface EpicService {
    void addEpic(EpicDTO epicDTO);
    void updateEpic(EpicDTO epicDTO);
    void deleteEpic(EpicDTO epicDTO);
    List<EpicDTO> getAllEpics();
    EpicDTO getEpicById(int id);
    void deleteAllEpics(List<EpicDTO> epicDTOs);
    Epic getEpicEntityById(int id);
}