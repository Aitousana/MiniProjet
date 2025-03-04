package com.example.miniproject.Services;

import com.example.miniproject.entities.Epic;
import com.example.miniproject.entities.UserStory;

import java.util.List;

public interface EpicService {
    public void AddEpic(Epic epic);
    public void DeleteEpic(Epic epic);
    public void UpdateEpic(Epic epic);
    public List<Epic> getAllEpic();
    public Epic getEpicById(int id);
    public void deleteAllEpic(List<Epic> epic);


}
