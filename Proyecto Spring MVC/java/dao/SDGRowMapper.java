package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.SDG;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class SDGRowMapper implements RowMapper<SDG> {
    public SDG mapRow(ResultSet rs, int rowNum) throws SQLException {
        SDG sdg = new SDG();
        sdg.setCodSDG(rs.getString("codsdg"));
        sdg.setName(rs.getString("name"));
        sdg.setDescription(rs.getString("description"));
        sdg.setRelevance(rs.getInt("relevance"));
        sdg.setURL(rs.getString("URL"));
        return sdg;
    }
}