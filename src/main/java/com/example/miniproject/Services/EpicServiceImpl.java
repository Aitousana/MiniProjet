package com.example.miniproject.Services;

import com.example.miniproject.DTO.EpicDTO;
import com.example.miniproject.Repositories.EpicRepo;
import com.example.miniproject.entities.Epic;
import com.example.miniproject.mapper.EpicMapper;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EpicServiceImpl implements EpicService {

    private final EpicRepo epicRepo;
    private final ProductBacklogService productBacklogService;

    public EpicServiceImpl(EpicRepo epicRepo, ProductBacklogService productBacklogService) {
        this.epicRepo = epicRepo;
        this.productBacklogService = productBacklogService;
    }

    @Override
    public void addEpic(EpicDTO epicDTO) {
        Epic epic = EpicMapper.INSTANCE.toEntity(epicDTO);
        epic.setProductBacklog(productBacklogService.getProductBacklogEntityById(epicDTO.getProductBacklogId()));
        epicRepo.save(epic);
    }

    @Override
    public void updateEpic(EpicDTO epicDTO) {
        Epic existingEpic = epicRepo.findById(epicDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Epic avec l'ID " + epicDTO.getId() + " n'existe pas"));
        existingEpic.setTitle(epicDTO.getTitle());
        if (epicDTO.getProductBacklogId() != existingEpic.getProductBacklog().getBacklogId()) {
            existingEpic.setProductBacklog(productBacklogService.getProductBacklogEntityById(epicDTO.getProductBacklogId()));
        }
        epicRepo.save(existingEpic);
    }

    @Override
    public void deleteEpic(EpicDTO epicDTO) {
        Epic epic = epicRepo.findById(epicDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException("Epic avec l'ID " + epicDTO.getId() + " n'existe pas"));
        epicRepo.delete(epic);
    }

    @Override
    public List<EpicDTO> getAllEpics() {
        return epicRepo.findAll().stream()
                .map(EpicMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public EpicDTO getEpicById(int id) {
        Epic epic = epicRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Epic avec l'ID " + id + " n'existe pas"));
        return EpicMapper.INSTANCE.toDTO(epic);
    }

    @Override
    public void deleteAllEpics(List<EpicDTO> epicDTOs) {
        epicDTOs.forEach(this::deleteEpic);
    }

    @Override
    public Epic getEpicEntityById(int id) {
        return epicRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Epic avec l'ID " + id + " n'existe pas"));
    }
}