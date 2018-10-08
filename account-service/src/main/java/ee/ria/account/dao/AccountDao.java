package ee.ria.account.dao;

import ee.ria.account.Account;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import java.sql.PreparedStatement;

@Repository
public class AccountDao extends JdbcDaoSupport {
    private static final Logger LOGGER = LoggerFactory.getLogger(AccountDao.class);

    private static final String INSERT_ACCOUNT = "INSERT INTO account(national_identity_number, device_id) VALUES (?, ?)";
    private static final String SELECT_LAST_ACCOUNT = "SELECT " +
            "DISTINCT ON (national_identity_number) device_id " +
            "FROM account " +
            "WHERE national_identity_number = ? " +
            "ORDER BY national_identity_number, created DESC ";
    @Autowired
    private DataSource dataSource;

    @PostConstruct
    private void initialize() {
        setDataSource(dataSource);
    }

    public void insertAccount(Account account) {
        if (getJdbcTemplate() != null) {
            int updated = getJdbcTemplate().update(con -> {
                PreparedStatement stmt = con
                        .prepareStatement(INSERT_ACCOUNT);
                stmt.setString(1, account.getNationalIdentityNumber());
                stmt.setString(2, account.getDeviceId());
                return stmt;
            });
            if (updated < 1) {
                LOGGER.warn("Could not insert into database!");
            }
        }
    }

    public String getDeviceId(String nationalIdentityNumber) {
        try {
            if (getJdbcTemplate() != null) {
                return getJdbcTemplate().queryForObject(SELECT_LAST_ACCOUNT, String.class, nationalIdentityNumber);
            }
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
        return null;
    }
}
