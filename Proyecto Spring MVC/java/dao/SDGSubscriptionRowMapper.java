package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.SDGSubscription;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public final class SDGSubscriptionRowMapper implements RowMapper<SDGSubscription> {
    public SDGSubscription mapRow(ResultSet rs, int rowNum) throws SQLException {
        SDGSubscription sdgSubscription = new SDGSubscription();
        sdgSubscription.setCodSDG(rs.getString("codSDG"));
        sdgSubscription.setMail(rs.getString("mail"));
        sdgSubscription.setFechaIni(rs.getObject("fechaIni", LocalDate.class));
        sdgSubscription.setFechaFin(rs.getObject("fechaFin", LocalDate.class));
        sdgSubscription.setComments(rs.getString("comments"));
        return sdgSubscription;
    }
}