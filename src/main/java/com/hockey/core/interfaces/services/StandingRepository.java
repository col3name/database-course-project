package com.hockey.core.interfaces.services;

import com.hockey.infrastructure.dto.StandingDTO;

import java.util.List;

public interface StandingRepository {
    List<StandingDTO> getStanding(Long seasonId);
}
