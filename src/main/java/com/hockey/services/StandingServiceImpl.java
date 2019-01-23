package com.hockey.services;

import com.hockey.core.interfaces.services.StandingRepository;
import com.hockey.core.interfaces.services.StandingService;
import com.hockey.infrastructure.dto.StandingDTO;

import java.util.List;

public class StandingServiceImpl implements StandingService {
    private final StandingRepository repository;

    public StandingServiceImpl(StandingRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<StandingDTO> getStanding(Long seasonId) {
        return repository.getStanding(seasonId);
    }
}