package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.SDG;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class SDGDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addSDG(SDG sdg) {
        jdbcTemplate.update("INSERT INTO SDG VALUES (?, ?, ?, ?, ?)",
                sdg.getCodSDG(), sdg.getName(), sdg.getDescription(),
                sdg.getRelevance(), sdg.getURL());
    }

    public void deleteSDG(SDG sdg) {
        jdbcTemplate.update("DELETE FROM SDG WHERE codsdg = ?",
                sdg.getCodSDG());
    }

    public void deleteSDG(String codSDG) {
        jdbcTemplate.update("DELETE FROM SDG WHERE codsdg = ?",
                codSDG);
    }

    public void updateSDG(SDG sdg) {
        jdbcTemplate.update("UPDATE SDG SET name = ?, description = ?, relevance = ?, URL = ?" +
                        "WHERE codsdg = ?", sdg.getName(), sdg.getDescription(),
                sdg.getRelevance(), sdg.getURL(), sdg.getCodSDG());
    }

    public SDG getSDG(String codSDG) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from SDG WHERE codSDG=?",
                    new SDGRowMapper(), codSDG);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }


    public List<SDG> getSomeSDGs(int currentPage, int pageSize) {
        try {
            int offset = currentPage * pageSize;
            return jdbcTemplate.query(
                    "SELECT * FROM SDG ORDER BY name ASC LIMIT ? OFFSET ?",
                    new SDGRowMapper(), pageSize, offset);
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<SDG> getAllSDGs() {
        try {
            return jdbcTemplate.query("SELECT * FROM SDG", new SDGRowMapper());
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }


    public List<String> getSDGsCod() {
        try {
            return jdbcTemplate.query(
                    "SELECT codsdg FROM SDG",
                    (rs, rowNum) -> rs.getString("codsdg"));
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
}