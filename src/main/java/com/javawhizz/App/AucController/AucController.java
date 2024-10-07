package com.javawhizz.App.AucController;

import com.javawhizz.App.entity.AucItem;
import com.javawhizz.App.entity.BoardDate;
import com.javawhizz.App.service.BoardAucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/idea")
public class AucController {

    @Autowired
    private BoardAucService boardAucService;

    /**
     * Get all AucItems for a given boardKey and date.
     */
    @GetMapping("/{boardKey}/{createDate}")
    public ResponseEntity<?> getAucItemsByKeyAndDate(@PathVariable String boardKey, @PathVariable String createDate) {
        List<AucItem> aucItems = boardAucService.getAucItemsByBoardKeyAndDate(boardKey, createDate);
        if (aucItems == null || aucItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No auction items found for the provided key and date.");
        }
        return ResponseEntity.ok(aucItems);
    }

    /**
     * Get all AucItems for a given boardKey sorted by date.
     */
    @GetMapping("/{boardKey}")
    public ResponseEntity<?> getAucItemsByBoardKeySorted(@PathVariable String boardKey) {
        List<AucItem> aucItems = boardAucService.getAucItemsByBoardKeySorted(boardKey);
        if (aucItems == null || aucItems.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("No auction items found for the provided board key.");
        }
        return ResponseEntity.ok(aucItems);
    }

    /**
     * Create a new AucItem associated with a specific boardKey and BoardDate (by createDate).
     */
    @PostMapping("/{boardKey}/{createDate}")
    public ResponseEntity<?> createAucItem(@PathVariable String boardKey,
                                           @RequestBody AucItem aucItem,
                                           @PathVariable String createDate) {
        try {
            AucItem createdAucItem = boardAucService.createAucItem(aucItem, boardKey, createDate);
            if (createdAucItem == null) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Board Key or Date does not exist.");
            }
            return new ResponseEntity<>(createdAucItem, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /**
     * Create a new boardKey.
     */
    @PostMapping("/create/{boardKey}")
    public ResponseEntity createBoardKey(@PathVariable String boardKey) {
        var result = boardAucService.createBoardKey(boardKey);
        if (!result) {
            return new ResponseEntity("Key already Exists!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity("Created", HttpStatus.OK);
    }

    @PostMapping("/create/{boardKey}/{createDate}")
    public ResponseEntity<?> createBoardDate(@PathVariable String boardKey, @PathVariable String createDate) {
        var boardDate = boardAucService.createBoardDate(boardKey, createDate);
        if (!boardDate) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Board Key does not exist.");
        }
        return ResponseEntity.ok(boardDate);
    }

    /**
     * Update an existing AucItem by id.
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAucItem(@PathVariable Long id, @RequestBody AucItem aucItem) {
        AucItem updatedAucItem = boardAucService.updateAucItem(id, aucItem);
        if (updatedAucItem == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("AucItem with id " + id + " not found.");
        }
        return ResponseEntity.ok(updatedAucItem);
    }

    /**
     * Delete an AucItem by id.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAucItem(@PathVariable Long id) {
        boardAucService.deleteAucItem(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/login/{boardKey}")
    public ResponseEntity<List<AucItem>> loginBoard(@PathVariable String boardKey) {
        List<AucItem> aucItems = boardAucService.loginBoard(boardKey);
        if (aucItems == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(aucItems);
    }

}
