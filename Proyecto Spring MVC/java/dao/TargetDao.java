package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Target;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public class TargetDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addTarget(Target target) {
        jdbcTemplate.update("INSERT INTO Target VALUES (?, ?, ?, ?, ?, ?)",
                target.getCodSDG(), target.getCodTarget(), target.getName(),
                target.getDescription(), target.getProgression(), target.getFechaPrevista());
    }

    public void deleteTarget(Target target) {
        jdbcTemplate.update("DELETE FROM Target WHERE codSDG = ? AND numTarget = ?",
                target.getCodSDG(), target.getCodTarget());
    }

    public void updateTarget(Target target) {
        jdbcTemplate.update("UPDATE Target SET name = ?, description = ?, progresion = ?, fechaPrevista = ?" +
                        "WHERE codSDG = ? AND numTarget = ?", target.getName(), target.getDescription(),
                target.getProgression(), target.getFechaPrevista(), target.getCodSDG(), target.getCodTarget());
    }

    public Target getTarget(int codSDG, int numTarget) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from Target WHERE codSDG=? AND numTarget=?",
                    new TargetRowMapper(), codSDG, numTarget);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
}
