package com.hockey.application.controller.api;

import com.hockey.core.entity.player.City;
import com.hockey.core.exception.EntityNotFoundException;
import com.hockey.core.interfaces.services.CityService;
import com.hockey.services.CityServiceImpl;
import com.hockey.infrastructure.repositories.CityRepositoryImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@RestController
public class CityController {
    private final CityService service;

    public CityController() throws SQLException {
        this.service = new CityServiceImpl(new CityRepositoryImpl());
    }

    @GetMapping("/city")
    public List<City> index() {
        return service.findAll();
    }

    @GetMapping("/city/{id}")
    public ResponseEntity<City> show(@PathVariable String id) {
        Long cityId = Long.parseLong(id);

        try {
            City city = service.findCityById(cityId);
            return new ResponseEntity<>(city, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/city")
    public ResponseEntity<City> create(@RequestParam Map<String, String> body) {
        String name = body.get("name");

        try {
            return new ResponseEntity<>(service.createCity(name) ? HttpStatus.OK : HttpStatus.NOT_FOUND);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/city/{id}")
    public boolean update(@PathVariable String id, @RequestParam Map<String, String> body) {
        Long cityId = Long.parseLong(id);
        String cityName = body.get("name");

        return service.updateCity(cityId, cityName);
    }

    @DeleteMapping("/city/{id}")
    public boolean delete(@PathVariable String id) {
        Long blogId = Long.parseLong(id);
        return service.deleteCity(blogId);
    }
}
