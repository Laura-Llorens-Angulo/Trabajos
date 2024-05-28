package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.InitiativeParticipation;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class InitiativeParticipationRowMapper implements RowMapper<InitiativeParticipation> {
    public InitiativeParticipation mapRow(ResultSet rs, int rowNum) throws SQLException {
        InitiativeParticipation initiativeParticipation = new InitiativeParticipation();
        initiativeParticipation.setMail(rs.getString("mail"));
        initiativeParticipation.setCodInitiative(rs.getString("codInitiative"));
        initiativeParticipation.setFechaIni(rs.getObject("fechaIni", LocalDate.class));
        initiativeParticipation.setFechaFin(rs.getObject("fechaFin", LocalDate.class));
        initiativeParticipation.setComments(rs.getString("comments"));
        return initiativeParticipation;
    }
}
