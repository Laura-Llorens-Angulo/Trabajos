package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Action;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class ActionRowMapper implements RowMapper<Action> {
    public Action mapRow(ResultSet rs, int rowNum) throws SQLException
    {
        Action action = new Action();
        action.setCodAction(rs.getString("codAction"));
        action.setCodInitiative(rs.getString("codInitiative"));
        action.setName(rs.getString("name"));
        action.setDescription(rs.getString("description"));
        action.setFechaIni(rs.getObject("fechaIni", LocalDate.class));
        action.setFechaFin(rs.getObject("fechaFin", LocalDate.class));

        return action;
    }
}
