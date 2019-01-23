package com.hockey.infrastructure.repositories;

import com.hockey.Application;
import com.hockey.core.entity.player.City;
import com.hockey.core.interfaces.Specification;
import com.hockey.infrastructure.specifications.FindByIdSpecification;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class CityRepositoryImpl extends BaseRepository<City> {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    public CityRepositoryImpl() throws SQLException {
        super();
    }

    @Override
    public List<City> findAll() {
        return getCities();
    }

    private List<City> getCities() {
        List<City> cities = new LinkedList<>();

        try {
            String query = "SELECT * FROM " + City.TABLE_NAME + " ;";
            ResultSet result = executeQuery(query);

            while (result.next()) {
                String name = result.getString("name");
                City city = new City(name);
                Long id = result.getLong("id");
                city.setId(id);
                cities.add(city);
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
//            e.printStackTrace();
        }

        return cities;
    }

    @Override
    public City findById(Long id) {
        FindByIdSpecification specification = new FindByIdSpecification();

        try {
            ResultSet result = executeQuery(specification.toSqlQuery());
            if (result.next()) {
                return createCity(result);
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return null;
    }

    private City createCity(ResultSet result) throws SQLException {
        City city = new City(result.getString("name"));
        city.setId(result.getLong("id"));
        return city;
    }

    @Override
    public boolean create(City item) {
        String sql = "INSERT INTO " + City.TABLE_NAME + " (name) VALUE (?);";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.executeLargeUpdate();

//            return new City(item.getName());
            return true;
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
            return false;
        }
//        return new City("");
    }

    @Override
    public boolean update(City item) {
        String sql = "UPDATE " + City.TABLE_NAME + " SET name = ? WHERE id = ?;";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, item.getName());
            statement.setLong(2, item.getId());
            statement.executeLargeUpdate();

            int result = executeUpdate(sql);
            return result == 1;
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        return delete(City.TABLE_NAME, id);
    }

    @Override
    public boolean delete(Specification specification) {

        return false;
    }

    @Override
    public List<City> query(Specification specification) {
        List<City> cities = new LinkedList<>();
        try {
            ResultSet result = executeQuery(specification.toSqlQuery());
            while (result.next()) {
                String name = result.getString("name");
                cities.add(new City(name));
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return cities;
    }
}