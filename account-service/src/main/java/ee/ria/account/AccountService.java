package ee.ria.account;

import ee.ria.account.dao.AccountDao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    @Autowired
    private AccountDao accountDao;

    public void registerAccount(Account account) {
        accountDao.insertAccount(account);
    }

    public String getDeviceId(String nationalIdentityNumber) {
        return accountDao.getDeviceId(nationalIdentityNumber);
    }
}
