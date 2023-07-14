package ru.yandex.practicum.filmorate.dao.db;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.dao.dao.MpaDao;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Mpa;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

@Slf4j
@Component
public class MpaDb implements MpaDao {
    private final JdbcTemplate jdbcTemplate;

    public MpaDb(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Collection<Mpa> findAll() {
        String sql = "select * from RATING";
        return jdbcTemplate.query(sql, ((rs, rowNum) -> makeMpa(rs)));
    }

    @Override
    public Mpa getById(int id) {
        validationId(id);
        String sql = "select * from RATING where RATING_ID = ?";
        List<Mpa> genreList = jdbcTemplate.query(sql, ((rs, rowNum) -> makeMpa(rs)), id);
        if (genreList.isEmpty()) {
            log.info("Рейтинг с id {} не найден", id);
            return null;
        } else {
            log.info("Найден рейтинг {}", genreList.get(0).getName());
            return genreList.get(0);
        }
    }

    @Override
    public void validationId(Integer id) {
        String sql = "SELECT COUNT(*) FROM RATING WHERE RATING_ID = ?";
        SqlRowSet resultSet = jdbcTemplate.queryForRowSet(sql, id);
        if (resultSet.next()) {
            if (resultSet.getInt("count(*)") == 0) {
                throw new NotFoundException(String.format("Рейтинг с id %s не существует", id));
            }
        }
    }

    private Mpa makeMpa(ResultSet rs) throws SQLException {
        return new Mpa(rs.getInt("rating_id"), rs.getString("name"));
    }
}
