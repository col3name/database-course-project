package com.hockey.core.interfaces.repositories;

import com.hockey.core.exception.EntityNotFoundException;
import com.hockey.core.interfaces.Specification;

import java.sql.SQLException;
import java.util.List;

public interface Repository<T> {
    List<T> findAll();

    T findById(Long id) throws Exception;

    boolean create(T item) throws SQLException;

    boolean update(T item);

    boolean delete(Long id);

    boolean delete(Specification specification);

    List<T> query(Specification specification);
}