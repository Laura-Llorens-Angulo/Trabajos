package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.Initiative;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class InitiativeDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addInitiative(Initiative initiative) {
        jdbcTemplate.update("INSERT INTO initiative VALUES (?, ?, ?, ?, ?, ?, ?,'WithoutValuing','TRUE')",
                initiative.getCodInitiative(), initiative.getCodSDG(), initiative.getMailResponsable(),
                initiative.getTitle(), initiative.getDescription(), initiative.getFechaIni(), initiative.getFechaFin());
    }

    public void deleteInitiative(Initiative initiative, LocalDate ahora) {
        jdbcTemplate.update("UPDATE initiative SET fechaFin = ? WHERE codInitiative = ?",
                ahora, initiative.getCodInitiative());
    }

    public void deleteInitiative(String codInitiative) {
        jdbcTemplate.update("UPDATE initiative SET active = 'FALSE' WHERE codInitiative = ?",
                codInitiative);
    }

    public void updateInitiative(Initiative initiative) {
        jdbcTemplate.update("UPDATE initiative SET codSDG = ?, mailResponsable = ?, title = ?, description = ?, fechaIni = ?, fechaFin = ?, state = ?" +
                        "WHERE codInitiative = ?", initiative.getCodSDG(), initiative.getMailResponsable(),
                initiative.getTitle(), initiative.getDescription(), initiative.getFechaIni(), initiative.getFechaFin(),
                initiative.getState(), initiative.getCodInitiative());
    }

    public Initiative getInitiative(String codInitiative) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from initiative WHERE codInitiative=? AND active='TRUE'",
                    new InitiativeRowMapper(), codInitiative);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }

    public List<Initiative> getAllInitiatives() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM initiative WHERE active='TRUE'",
                    new InitiativeRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<Initiative> getInitiatives(int currentPage, int pageSize) {
        try {
            int offset = currentPage * pageSize;
            return jdbcTemplate.query(
                    "SELECT * FROM initiative WHERE active='TRUE' ORDER BY title ASC LIMIT ? OFFSET ?",
                    new InitiativeRowMapper(), pageSize, offset);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<String> getInitiativesCod() {
        try {
            return jdbcTemplate.query(
                    "SELECT codInitiative FROM initiative WHERE codInitiative LIKE 'ini%'",
                    (rs, rowNum) -> rs.getString("codInitiative"));
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<Initiative> getPending(){
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM initiative WHERE COALESCE(state,'WithoutValuing') = 'WithoutValuing' AND active='TRUE' ",
                    new InitiativeRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<Initiative> getAllAcceptedInitiatives() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM initiative WHERE state = 'Accepted' AND active='TRUE' AND active='TRUE'",
                    new InitiativeRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<Initiative> getAcceptedInitiatives(int currentPage, int pageSize) {
        try {
            int offset = currentPage * pageSize;
            return jdbcTemplate.query(
                    "SELECT * FROM initiative WHERE state = 'Accepted' AND active='TRUE' ORDER BY title ASC LIMIT ? OFFSET ?",
                    new InitiativeRowMapper(), pageSize, offset);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<Initiative> getPendingInitiatives(int currentPage, int pageSize) {
        try {
            int offset = currentPage * pageSize;
            return jdbcTemplate.query(
                    "SELECT * FROM initiative WHERE COALESCE(state,'WithoutValuing') = 'WithoutValuing' AND active='TRUE' ORDER BY title ASC LIMIT ? OFFSET ?",
                    new InitiativeRowMapper(), pageSize, offset);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
    public List<String> getMyCreatedInitiatives(String userEmail) {
        try {
            return jdbcTemplate.query(
                    "SELECT codInitiative FROM Initiative WHERE mailResponsable = ? AND active='TRUE'",
                    (rs, rowNum) -> rs.getString("codInitiative"),
                    userEmail
            );
        } catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<Initiative> getMyAcceptedInitiatives(int currentPage, int pageSize,String userEmail) {
        try {
            int offset = currentPage * pageSize;
            return jdbcTemplate.query(
                    "SELECT * FROM initiative WHERE active = 'TRUE' AND mailResponsable = ? ORDER BY title ASC LIMIT ? OFFSET ?",
                    new InitiativeRowMapper(),userEmail,pageSize, offset);
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }
    public List<String> getMyDeletedInitiatives(String userEmail) {
        try {
            return jdbcTemplate.query(
                    "SELECT codInitiative FROM Initiative WHERE mailResponsable = ? AND active='FALSE'",
                    (rs, rowNum) -> rs.getString("codInitiative"),
                    userEmail
            );
        } catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    public List<Initiative> getAllDeletedInitiatives() {
        try {
            return jdbcTemplate.query(
                    "SELECT * FROM initiative AND active='False'",
                    new InitiativeRowMapper());
        }
        catch(EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

}
