package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Action;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ActionDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    // Insertar una nueva Action a la BD
    public void addAction(Action action) {
        jdbcTemplate.update("INSERT INTO action VALUES (?, ?, ?, ?, ?, ?)",
                action.getCodAction(), action.getCodInitiative(), action.getName(), action.getDescription()
                , action.getFechaIni(), action.getFechaFin());
    }

    // Eliminar una Action de la BD
    public void deleteAction(String codAction) {
        jdbcTemplate.update("DELETE FROM action WHERE codAction = ?", codAction);
    }

    // Actualizar una Action, ya existente, de la BD
    public void updateAction(Action action) {
        jdbcTemplate.update("UPDATE Action SET name = ?, fechaIni = ?, fechaFin = ?, description = ?" +
                "WHERE codAction = ?", action.getName(),action.getFechaIni(), action.getFechaFin(),
                action.getDescription(), action.getCodAction());
    }

    // Obtener una Action concreta
    public Action getAction(String codAction) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from action WHERE codAction=?",
                    new ActionRowMapper(), codAction);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    // Obtener todas las Actions
    public List<Action> getActions() {
        try {
            return jdbcTemplate.query("SELECT * from Classificacio",
                    new ActionRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    // Obtener todas las Actions de una Initiative
    public List<Action> getActionsInitiative(String codInitiative) {
        try {
            return this.jdbcTemplate.query(
                    "SELECT * FROM action WHERE codInitiative=?",
                    new ActionRowMapper(), codInitiative);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<String> getActionsCod() {
        try {
            return jdbcTemplate.query(
                    "SELECT codAction FROM action WHERE codAction LIKE 'act%'",
                    (rs, rowNum) -> rs.getString("codAction"));
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}
