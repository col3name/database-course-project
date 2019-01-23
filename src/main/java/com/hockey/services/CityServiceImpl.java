package com.hockey.services;

import com.hockey.Application;
import com.hockey.core.entity.player.City;
import com.hockey.core.exception.EntityNotFoundException;
import com.hockey.core.interfaces.repositories.Repository;
import com.hockey.core.interfaces.services.CityService;

import java.sql.SQLException;
import java.util.List;
import java.util.logging.Logger;

public class CityServiceImpl implements CityService {
    public static final Logger LOG = Logger.getLogger(Application.class.getName());

    private final Repository<City> repository;

    public CityServiceImpl(Repository<City> repository) {
        this.repository = repository;
    }

    @Override
    public List<City> findAll() {
        return repository.findAll();
    }

    @Override
    public City findCityById(Long id) throws EntityNotFoundException {
        try {
            return repository.findById(id);
        } catch (Exception e) {
            LOG.info("not enough city with id '" + id + "'");
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Override
    public boolean createCity(String name) throws EntityNotFoundException {
        try {
            return repository.create(new City(name));
        } catch (SQLException e) {
            throw new EntityNotFoundException(e.getMessage());
        }
    }

    @Override
    public boolean updateCity(Long id, String name) {
        City item = new City(name);
        item.setId(id);
        return repository.update(item);
    }

    @Override
    public boolean deleteCity(Long id) {
        return repository.delete(id);
    }
}
