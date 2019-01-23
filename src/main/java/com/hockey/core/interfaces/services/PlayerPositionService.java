package com.hockey.core.interfaces.services;

import com.hockey.core.entity.player.PlayerPosition;
import com.hockey.core.exception.EntityNotFoundException;
import com.hockey.core.exception.InvalidOperationException;

import java.util.List;

public interface PlayerPositionService {

    List<PlayerPosition> findAll();

    PlayerPosition findPlayerPositionById(Long id) throws Exception;

    boolean createPlayerPosition(String name) throws InvalidOperationException;

    boolean updatePlayerPosition(Long id, String name);

    boolean deletePlayerPosition(Long id);
}
