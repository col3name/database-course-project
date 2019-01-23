package com.hockey.core.interfaces.services;

import com.hockey.core.entity.player.City;
import com.hockey.core.exception.EntityNotFoundException;

import java.util.List;

public interface CityService {
    List<City> findAll();

    City findCityById(Long id) throws EntityNotFoundException;

    boolean createCity(String name) throws EntityNotFoundException;

    boolean updateCity(Long id, String name);

    boolean deleteCity(Long id);
}
