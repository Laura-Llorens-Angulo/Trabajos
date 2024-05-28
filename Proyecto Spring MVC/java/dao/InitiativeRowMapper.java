package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Initiative;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class InitiativeRowMapper implements RowMapper<Initiative> {
    public Initiative mapRow(ResultSet rs, int rowNum) throws SQLException {
        Initiative initiative = new Initiative();
        initiative.setCodInitiative(rs.getString("codInitiative"));
        initiative.setCodSDG(rs.getString("codSDG"));
        initiative.setMailResponsable(rs.getString("mailResponsable"));
        initiative.setTitle(rs.getString("title"));
        initiative.setDescription(rs.getString("description"));
        initiative.setFechaIni(rs.getObject("fechaIni", LocalDate.class));
        initiative.setFechaFin(rs.getObject("fechaFin", LocalDate.class));
        initiative.setState(rs.getString("state"));
        initiative.setActive(rs.getString("active"));
        return initiative;
    }
}
