package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.ActionParticipation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActionParticipationDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addActionParticipation(ActionParticipation actionParticipation) {
        jdbcTemplate.update("INSERT INTO actionParticipation VALUES (?, ?, ?, ?, ?)",
                actionParticipation.getMail(), actionParticipation.getCodAction(), actionParticipation.getFechaIni(),
                actionParticipation.getFechaFin(), actionParticipation.getComments());
    }

    public void deleteActionParticipation(ActionParticipation actionParticipation) {
        jdbcTemplate.update("DELETE FROM actionParticipation WHERE mail = ? AND codAction = ?",
                actionParticipation.getMail(), actionParticipation.getCodAction());
    }

    public void updateActionParticipation(ActionParticipation actionParticipation) {
        jdbcTemplate.update("UPDATE actionParticipation SET fechaIni = ?, fechaFin = ?, comments = ?" +
                "WHERE mail = ? AND codAction = ?", actionParticipation.getFechaIni(), actionParticipation.getFechaFin(),
                actionParticipation.getComments(), actionParticipation.getMail(), actionParticipation.getCodAction());
    }

    public ActionParticipation getActionParticipation(String codAction, String mail) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from actionParticipation WHERE codAction=? AND mail=?",
                    new ActionParticipationRowMapper(), codAction, mail);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<ActionParticipation> getActionParticipations(String codAction) {
        try {
            return jdbcTemplate.query("SELECT * from actionParticipation WHERE codAction=?",
                    new ActionParticipationRowMapper(), codAction);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public boolean isUserSubscribed(String codAction, String userEmail) {
        try {
            jdbcTemplate.queryForObject("SELECT * FROM actionParticipation WHERE mail=? AND codAction=?",
                    new ActionParticipationRowMapper(), userEmail, codAction);
            return true;
        } catch(EmptyResultDataAccessException e) {
            return false;
        }
    }
}
