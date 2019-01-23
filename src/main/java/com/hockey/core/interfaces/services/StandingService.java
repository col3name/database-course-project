package com.hockey.core.interfaces.services;

import com.hockey.infrastructure.dto.StandingDTO;

import java.util.List;

public interface StandingService {
    List<StandingDTO> getStanding(Long seasonId);
}
