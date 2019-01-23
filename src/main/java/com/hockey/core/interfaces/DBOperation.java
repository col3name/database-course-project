package com.hockey.core.interfaces;

import com.hockey.core.entity.player.City;

import java.util.List;

public interface DBOperation<T> {
    List<City> findAll();

    T findById(Long id);

    T create(T entity);

    T edit(T entity);

    T delete(T entity);
}
