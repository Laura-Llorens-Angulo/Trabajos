package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.InitiativeParticipation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InitiativeParticipationDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addIniciativeParticipation(InitiativeParticipation initiativeParticipation) {
        jdbcTemplate.update("INSERT INTO initiativeParticipation VALUES (?, ?, ?)",
                initiativeParticipation.getMail(), initiativeParticipation.getCodInitiative(), initiativeParticipation.getFechaIni());
    }

    public void deleteInitiativeParticipation(InitiativeParticipation initiativeParticipation) {
        jdbcTemplate.update("DELETE FROM initiativeParticipation WHERE codInitiative = ? AND mail = ?",
                initiativeParticipation.getCodInitiative(), initiativeParticipation.getMail());
    }

    public void updateInitiativeParticipation(InitiativeParticipation initiativeParticipation) {
        jdbcTemplate.update("UPDATE initiativeParticipation SET fechaIni = ?, fechaFin = ?, comments = ?" +
                        "WHERE codIni = ? AND mail = ?", initiativeParticipation.getFechaIni(), initiativeParticipation.getFechaFin(),
                initiativeParticipation.getComments(), initiativeParticipation.getCodInitiative(), initiativeParticipation.getMail());
    }

    public boolean isUserSubscribed(String codInitiative, String userEmail) {
        try {
            jdbcTemplate.queryForObject("SELECT * FROM initiativeParticipation WHERE codInitiative=? AND mail=?",
                    new InitiativeParticipationRowMapper(), codInitiative, userEmail);
            return true;
        } catch(EmptyResultDataAccessException e) {
            return false;
        }
    }

    public List<InitiativeParticipation> getParticipants(String codInitiative) {
        try {
            return jdbcTemplate.query("SELECT * FROM initiativeParticipation WHERE codInitiative=?",
                    new InitiativeParticipationRowMapper(), codInitiative);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
    public InitiativeParticipation getInitiativeParticipation(String codInitiative, String userEmail) {
        try {
            return jdbcTemplate.queryForObject("SELECT * FROM initiativeParticipation WHERE codInitiative=? AND mail=?",
                    new InitiativeParticipationRowMapper(), codInitiative, userEmail);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<String> getMySubs(String userEmail) {
        try {
            return jdbcTemplate.query(
                    "SELECT codInitiative FROM initiativeParticipation WHERE mail=?",
                    (rs, rowNum) -> rs.getString("codInitiative"),
                    userEmail
            );
        } catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
