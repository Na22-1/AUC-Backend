package com.javawhizz.App.AucController;

import com.javawhizz.App.entity.AucItem;
import com.javawhizz.App.service.AucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/idea")
public class AucController {
    @Autowired
    private AucService aucService;

    @GetMapping("/{boardKey}")
    public List<AucItem> getAucItems(@PathVariable String boardKey) {
        return aucService.getAucItems(boardKey);
    }

    @PostMapping("/{boardKey}")
    public AucItem createAucItem(@PathVariable String boardKey, @RequestBody AucItem aucItem) {
        return aucService.createAucItem(aucItem, boardKey);
    }

    @PostMapping("/create/{boardKey}")
    public ResponseEntity createBoardKey(@PathVariable String boardKey) {
        var result = aucService.createBoardKey(boardKey);
        if (!result) {
            return new ResponseEntity("Key already Exists!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Created", HttpStatus.OK);
    }

    @GetMapping("/login/{boardKey}")
    public ResponseEntity<List<AucItem>> loginBoard(@PathVariable String boardKey) {
        List<AucItem> aucItems = aucService.loginBoard(boardKey);
        if (aucItems == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(aucItems);
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