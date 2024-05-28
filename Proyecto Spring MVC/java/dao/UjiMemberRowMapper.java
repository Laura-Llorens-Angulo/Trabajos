package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.UjiMember;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public final class UjiMemberRowMapper implements RowMapper<UjiMember> {
    public UjiMember mapRow(ResultSet rs, int rowNum) throws SQLException {
        UjiMember ujiMember = new UjiMember();
        ujiMember.setMail(rs.getString("mail"));
        ujiMember.setDni(rs.getString("dni"));
        ujiMember.setName(rs.getString("name"));
        ujiMember.setPw(rs.getString("pw"));
        ujiMember.setType(rs.getString("type").toUpperCase());
        return ujiMember;
    }
}
