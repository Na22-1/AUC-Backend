package com.controller.AucController;

import com.controller.entity.AucItem;
import com.controller.service.AucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/idea")
public class AucController {
    @Autowired
    private AucService aucService;

    @GetMapping
    public List<AucItem> getAucItems() {
        return aucService.getAucItems();
    }

    @PostMapping
    public AucItem createAucItem(@RequestBody AucItem aucItem) {
        return aucService.createAucItem(aucItem);
    }


    @PutMapping("/{id}")
    public AucItem updateAucItem(@PathVariable Long id, @RequestBody AucItem aucItem) {
        return aucService.updateAucItem(id, aucItem);
    }

    @DeleteMapping("/{id}")
    public void deleteAucItem(@PathVariable Long id) {
        aucService.deleteAucItem(id);
    }
}