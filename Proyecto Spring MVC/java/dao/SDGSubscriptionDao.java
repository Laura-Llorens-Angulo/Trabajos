package es.uji.ei1027.clubesportiu.dao;

import es.uji.ei1027.clubesportiu.model.SDGSubscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class SDGSubscriptionDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public void addSDGSubscription(SDGSubscription sdgSubscription) {
        jdbcTemplate.update("INSERT INTO SDGSubscription VALUES (?, ?, ?, ?, ?)",
                sdgSubscription.getCodSDG(), sdgSubscription.getMail(), sdgSubscription.getFechaIni(),
                sdgSubscription.getFechaFin(), sdgSubscription.getComments());
    }

    public void deleteSDGSubscription(SDGSubscription sdgSubscription) {
        jdbcTemplate.update("DELETE FROM SDGSubscription WHERE codSDG = ? AND mail = ?",
                sdgSubscription.getCodSDG(), sdgSubscription.getMail());
    }

    public void updateSDGSubscription(SDGSubscription sdgSubscription) {
        jdbcTemplate.update("UPDATE SDGSubscription SET fechaIni = ?, fechaFin = ?, comments = ?" +
                        "WHERE codSDG = ? AND mail = ?", sdgSubscription.getFechaIni(), sdgSubscription.getFechaFin(),
                sdgSubscription.getComments(), sdgSubscription.getCodSDG(), sdgSubscription.getMail());
    }
    public boolean isUserSubscribed(String codSDG, String userEmail) {
        try {
            jdbcTemplate.queryForObject("SELECT * FROM sdgsubscription WHERE codsdg=? AND mail=?",
                    new SDGSubscriptionRowMapper(), codSDG, userEmail);
            return true;
        } catch(EmptyResultDataAccessException e) {
            return false;
        }
    }

    public SDGSubscription getSDGSubscription(String codSDG, String mail) {
        try {
            return jdbcTemplate.queryForObject("SELECT * from sdgsubscription WHERE codSDG=? AND mail=?",
                    new SDGSubscriptionRowMapper(), codSDG, mail);
        }
        catch(EmptyResultDataAccessException e) {
            return null;
        }
    }
}
