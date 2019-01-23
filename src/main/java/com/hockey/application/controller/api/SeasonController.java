package com.hockey.application.controller.api;

import com.hockey.core.entity.league.Season;
import com.hockey.core.exception.EntityNotFoundException;
import com.hockey.core.interfaces.services.SeasonService;
import com.hockey.services.SeasonServiceImpl;
import com.hockey.infrastructure.repositories.SeasonRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class SeasonController {
    private final SeasonService service;

    public SeasonController() throws SQLException {
        this.service = new SeasonServiceImpl(new SeasonRepositoryImpl());
    }

    @GetMapping("/seasons")
    public List<Season> index() {
        return service.findAll();
    }

    @GetMapping("/seasons/{id}")
    public ResponseEntity<Season> show(@PathVariable String id) {
        Long seasonId = Long.parseLong(id);

        try {
            return new ResponseEntity<>( service.findSeasonById(seasonId), HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/seasons")
    public ResponseEntity create(@RequestParam Map<String, String> body) {
        try {
            String startDate = body.get("startDate");
            String endDate = body.get("endDate");
            Date start = Date.valueOf(startDate);
            Date end = Date.valueOf(endDate);
            service.createSeason(start, end);
            return new ResponseEntity(HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/seasons/{id}")
    public boolean update(@PathVariable String id, @RequestParam Map<String, String> body) {
        Long seasonId = Long.parseLong(id);
        String seasonName = body.get("name");

        return service.updateSeason(seasonId, seasonName);
    }

    @DeleteMapping("/seasons/{id}")
    public boolean delete(@PathVariable String id) {
        Long blogId = Long.parseLong(id);
        return service.deleteSeason(blogId);
    }
}
