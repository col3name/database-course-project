package com.hockey.infrastructure.repositories;

import com.hockey.Application;
import com.hockey.core.entity.league.Season;
import com.hockey.core.interfaces.Specification;
import com.hockey.infrastructure.specifications.FindByIdSpecification;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class SeasonRepositoryImpl extends BaseRepository<Season> {
    private static final Logger LOG = Logger.getLogger(Application.class.getName());

    public SeasonRepositoryImpl() throws SQLException {
        super();
    }

    @Override
    public List<Season> findAll() {
        List<Season> seasons = new LinkedList<>();

        try {
            String query = "SELECT * FROM " + Season.TABLE_NAME + " ;";
            ResultSet result = executeQuery(query);
            while (result.next()) {
                String startStr = result.getString("start");
                Date start = Date.valueOf(startStr);

                String endStr = result.getString("end");
                Date end = Date.valueOf(endStr);

                Season city = new Season(start, end);
                Long id = result.getLong("id");

                city.setId(id);
                seasons.add(city);
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return seasons;
    }

    @Override
    public Season findById(Long id) {
        FindByIdSpecification specification = new FindByIdSpecification();

        try {
            ResultSet result = executeQuery(specification.toSqlQuery());
            if (result.next()) {
                return createSeason(result.getDate("start"), result.getDate("end"), result.getLong("id"));
            }
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return null;
    }

    private Season createSeason(Date startDate, Date endDate, long id) {
        Season season = new Season(startDate, endDate);
        season.setId(id);
        return season;
    }

    @Override
    public boolean create(Season item) throws SQLException {
        return create(item, item.getStart(), item.getEnd());
    }

    public boolean create(Season item, Date start, Date end) throws SQLException {

        String sql = "INSERT INTO " + Season.TABLE_NAME + " (start, end) VALUE ('" + start + ", " + end + "');";
        int affectedRows = executeUpdate(sql);

        if (affectedRows == 0) {
            throw new SQLException("Creating user failed, no rows affected.");
        }

        Season city = new Season(item.getStart(), item.getEnd());
        city.setId(getLastInsertedId());
        return true;
    }

    @Override
    public boolean update(Season item) {
        try {
            String sql = "UPDATE " + Season.TABLE_NAME + " SET start = '" + item.getStart() + "'," +
                    " end = '" + item.getEnd() + "' WHERE id = " + item.getId() + ";";

            return executeUpdate(sql) == 1;
        } catch (SQLException e) {
            LOG.warning(e.getMessage());
        }

        return false;
    }

    @Override
    public boolean delete(Long id) {
        return delete(Season.TABLE_NAME, id);
    }

    @Override
    public boolean delete(Specification specification) {

        return false;
    }

    @Override
    public List<Season> query(Specification specification) {
        List<Season> cities = new LinkedList<>();
        try {
            ResultSet result = executeQuery(specification.toSqlQuery());
//            while (result.next()) {
//                String name = result.getString("start");
//                cities.add(new Season(name));
//            }
        } catch (SQLException e) {
             LOG.warning(e.getMessage());
        }

        return cities;
    }
}