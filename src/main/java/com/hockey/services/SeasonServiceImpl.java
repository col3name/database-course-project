package com.hockey.services;

import com.hockey.core.entity.league.Season;
import com.hockey.core.exception.EntityException;
import com.hockey.core.exception.EntityNotFoundException;
import com.hockey.core.interfaces.repositories.Repository;
import com.hockey.core.interfaces.services.SeasonService;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

public class SeasonServiceImpl implements SeasonService {
    private final Repository<Season> repository;

    public SeasonServiceImpl(Repository<Season> repository) {
        this.repository = repository;
    }

    @Override
    public List<Season> findAll() {
        return repository.findAll();
    }

    @Override
    public Season findSeasonById(Long id) throws Exception {
        return repository.findById(id);
    }

    @Override
    public boolean createSeason(Date start, Date end) throws EntityException {
        try {
            return repository.create(new Season(start, end));
        } catch (SQLException e) {
            throw new EntityException("Failed create season");
        }
    }

    @Override
    public boolean updateSeason(Long id, String name) {
        return false;
    }

    @Override
    public boolean deleteSeason(Long id) {
        return false;
    }
}
