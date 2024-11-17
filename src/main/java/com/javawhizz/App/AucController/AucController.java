package com.javawhizz.App.AucController;

import com.javawhizz.App.entity.AucItem;
import com.javawhizz.App.entity.BoardDate;
import com.javawhizz.App.entity.BoardKey;
import com.javawhizz.App.service.AucService;
import com.javawhizz.App.service.BoardLoginService;
import com.javawhizz.App.utility.BoardDateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/idea")
public class AucController {
    @Autowired
    private AucService aucService;

    @Autowired
    private BoardLoginService boardLoginService;

    @Autowired
    private BoardDateService boardDateService;
/*
    @GetMapping("/{boardKey}/{boardDate}")
    public ResponseEntity<List<AucItem>> getAucItems(@PathVariable String boardKey, @PathVariable String boardDate) {
        List<AucItem> aucItems = aucService.getAucItems(boardKey, boardDate);

        if (aucItems == null || aucItems.isEmpty()) {
            return ResponseEntity.notFound().build(); // Return 404 Not Found
        }

        return ResponseEntity.ok(aucItems);
    }
*/
    @GetMapping("/{boardKey}/{boardDate}")
    public ResponseEntity<List<AucItem>> getAucItems(@PathVariable String boardKey, @PathVariable String boardDate) {

        List<AucItem> aucItems = aucService.getAucItems(boardKey, boardDate);

        if (aucItems.isEmpty()) {
            return ResponseEntity.ok(Collections.emptyList());
        }

        return ResponseEntity.ok(aucItems);
    }
    @PostMapping("/{boardKey}/{boardDate}")
    public AucItem addAucItem(@PathVariable String boardKey,
                              @PathVariable String boardDate,
                              @RequestBody AucItem aucItem) {
        return aucService.createAucItem(boardKey, boardDate, aucItem);
    }

    @PutMapping("/{id}")
    public AucItem updateAucItem(@PathVariable Long id, @RequestBody AucItem aucItem) {
        return aucService.updateAucItem(id, aucItem);
    }

    @DeleteMapping("/{id}")
    public void deleteAucItem(@PathVariable Long id) {
        aucService.deleteAucItem(id);
    }

    @PostMapping("/createNewBoard/{boardKey}/{boardDate}")
    public ResponseEntity<List<AucItem>> createNewBoard(@PathVariable String boardKey, @PathVariable String boardDate) {
        List<AucItem> aucItems =  boardDateService.checkDateWithKey(boardKey, boardDate);

        if (aucItems == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(aucItems);
    }

    @PostMapping("/create/{boardKey}")
    public ResponseEntity<String> createBoardKey(@PathVariable String boardKey) {
        var result = boardLoginService.createBoardKey(boardKey);
        if (!result) {
            return new ResponseEntity<>("Key already Exists!", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Created", HttpStatus.OK);
    }

    @GetMapping("/login/{boardKey}/{boardDate}")
    public ResponseEntity<List<AucItem>> loginBoard(@PathVariable String boardKey, @PathVariable String boardDate) {
        BoardKey key = new BoardKey();
        key.setBoardKey(boardKey);

        List<AucItem> aucItems = boardLoginService.loginBoard(boardKey, boardDate);
        if (aucItems == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(aucItems);
    }
}

