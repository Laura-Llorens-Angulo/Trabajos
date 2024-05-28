package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.ActionParticipation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class ActionParticipationRowMapper implements RowMapper<ActionParticipation> {
    public ActionParticipation mapRow(ResultSet rs, int rowNum) throws SQLException {
        ActionParticipation actionParticipation = new ActionParticipation();
        actionParticipation.setMail(rs.getString("mail"));
        actionParticipation.setCodAction(rs.getString("codAction"));
        actionParticipation.setFechaIni(rs.getObject("fechaIni", LocalDate.class));
        actionParticipation.setFechaFin(rs.getObject("fechaFin", LocalDate.class));
        actionParticipation.setComments(rs.getString("comments"));
        return actionParticipation;
    }
}
