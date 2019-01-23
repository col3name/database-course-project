package com.hockey.application.controller.api;

import com.hockey.core.entity.player.PlayerPosition;
import com.hockey.core.exception.EntityNotFoundException;
import com.hockey.core.exception.InvalidOperationException;
import com.hockey.core.interfaces.services.PlayerPositionService;
import com.hockey.services.PlayerPositionServiceImpl;
import com.hockey.infrastructure.repositories.PlayerPositionRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class PlayerPositionController {
    private final PlayerPositionService service;

    public PlayerPositionController() throws SQLException {
        this.service = new PlayerPositionServiceImpl(new PlayerPositionRepositoryImpl());
    }

    @GetMapping("/playerPositions")
    public List<PlayerPosition> index() {
        return service.findAll();
    }

    @GetMapping("/playerPositions/{id}")
    public ResponseEntity show(@PathVariable String id) {
        Long cityId = Long.parseLong(id);

        try {
//            return service.findPlayerPositionById(cityId);
            return new ResponseEntity(service.findPlayerPositionById(cityId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/playerPositions")
    public ResponseEntity create(@RequestParam Map<String, String> body) {
        String name = body.get("name");

        try {
            return new ResponseEntity(service.createPlayerPosition(name), HttpStatus.OK);
        } catch (InvalidOperationException e) {
            e.printStackTrace();
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/playerPositions/{id}")
    public boolean update(@PathVariable String id, @RequestParam Map<String, String> body) {
        Long cityId = Long.parseLong(id);
        String cityName = body.get("name");

        return service.updatePlayerPosition(cityId, cityName);
    }

    @DeleteMapping("/playerPositions/{id}")
    public boolean delete(@PathVariable String id) {
        Long blogId = Long.parseLong(id);
        return service.deletePlayerPosition(blogId);
    }
}
