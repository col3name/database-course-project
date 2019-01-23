package com.hockey.core.interfaces.services;

import com.hockey.core.entity.league.Season;
import com.hockey.core.exception.EntityException;
import com.hockey.core.exception.EntityNotFoundException;

import java.sql.Date;
import java.util.List;

public interface SeasonService {
    List<Season> findAll();

    Season findSeasonById(Long id) throws Exception;

    boolean createSeason(Date start, Date end) throws EntityException;

    boolean updateSeason(Long id, String name);

    boolean deleteSeason(Long id);
}
