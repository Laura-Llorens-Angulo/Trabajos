package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Progression;
import es.uji.ei1027.clubesportiu.model.Target;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class TargetRowMapper implements RowMapper<Target> {
    public Target mapRow(ResultSet rs, int rowNum) throws SQLException {
        Target target = new Target();
        target.setCodSDG(rs.getString("codSDG"));
        target.setCodTarget(rs.getString("codTarget"));
        target.setName(rs.getString("name"));
        target.setDescription(rs.getString("description"));
        target.setProgression(Progression.valueOf(rs.getString("progresion").toUpperCase()));
        target.setFechaPrevista(rs.getObject("fechaPrevista", LocalDate.class));
        return target;
    }
}
