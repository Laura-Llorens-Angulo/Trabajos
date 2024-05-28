package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.UjiMember;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class UjiMemberDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addUjiMember(UjiMember ujiMember) {
        jdbcTemplate.update("INSERT INTO UjiMember VALUES (?, ?, ?, ?, ?)",
                ujiMember.getMail(), ujiMember.getDni(), ujiMember.getName(), ujiMember.getPw(),
                ujiMember.getType());
    }

    public void deleteUjiMember(UjiMember ujiMember) {
        jdbcTemplate.update("DELETE FROM UjiMember WHERE mail = ?",
                ujiMember.getMail());
    }

    public void updateUjiMember(UjiMember ujiMember) {
        jdbcTemplate.update("UPDATE UjiMember SET dni = ?, name = ?, pw = ?, type = ?" +
                        "WHERE mail = ?", ujiMember.getDni(), ujiMember.getName(), ujiMember.getPw(),
                ujiMember.getType(), ujiMember.getMail());
    }

    public UjiMember getUjiMember(String mail) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from UjiMember WHERE mail=?",
                    new UjiMemberRowMapper(), mail);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
}
