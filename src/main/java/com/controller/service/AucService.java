package com.controller.service;

import com.controller.entity.AucItem;
import com.controller.repository.AucRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AucService {
    @Autowired
    private AucRepository aucRepository;
    public List<AucItem> getAucItems() {
        return aucRepository.findAll();
    }

    public AucItem getAucItem(Long id) {
        return aucRepository.findById(id).orElse(null);
    }

    public AucItem createAucItem(AucItem aucItem) {
        var save = aucRepository.save(aucItem);
        return save;
    }

    public AucItem updateAucItem(Long id, AucItem updatedAucItem) {
        AucItem existingAucItem = getAucItem(id);

        if (existingAucItem == null) {
            return null;
        }
        // Map the fields from the IdeaRequest object to the Idea entity
        existingAucItem.setCanvasBox(updatedAucItem.getCanvasBox());
        existingAucItem.setDescription(updatedAucItem.getDescription());
        var save = aucRepository.save(existingAucItem);

        return save;
    }

    public void deleteAucItem(Long id) {
        aucRepository.deleteById(id);
    }
}