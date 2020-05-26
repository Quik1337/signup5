package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Account;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.User;
import se.expleostockholm.signup.repository.AccountMapper;
import se.expleostockholm.signup.repository.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;
    private final UserMapper userMapper;

    public List<Account> getAccounts() {
        return accountMapper.getAccounts();
    }

    public Account getAccountById(Long id) throws Exception {
        return accountMapper.getAccountById(id).orElseThrow(() -> new Exception("No account found!"));
    }

    public Account getAccountByUserId(Long user_id) throws Exception {
        return accountMapper.getAccountByUserId(user_id).orElseThrow(() -> new Exception("No account found!"));
    }

    public void createAccount(Account account) throws Exception {

        Optional<User> optionalUser = userMapper.getUserByEmail(account.getUser().getEmail());

        if (optionalUser.isEmpty()) {

            userMapper.createUser(account.getUser());
            optionalUser = userMapper.getUserByEmail(account.getUser().getEmail());
        }

        optionalUser.ifPresent(account::setUser);

        accountMapper.createAccount(account);
    }

}