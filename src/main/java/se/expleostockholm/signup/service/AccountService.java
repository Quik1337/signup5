package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Account;
import se.expleostockholm.signup.domain.User;
import se.expleostockholm.signup.exception.*;
import se.expleostockholm.signup.repository.AccountMapper;
import se.expleostockholm.signup.repository.UserMapper;

import java.util.List;
import java.util.Optional;

import static se.expleostockholm.signup.service.ServiceUtil.isValidEmail;

@Service
@AllArgsConstructor
public class AccountService {

    private final AccountMapper accountMapper;
    private final UserMapper userMapper;

    public List<Account> getAllAccounts() {
        return accountMapper.getAllAccounts();
    }

    public Account getAccountById(Long id) {
        return accountMapper.getAccountById(id).orElseThrow(() -> new AccountNotFoundException("No account found!"));
    }

    public Account getAccountByUserEmail(String userEmail) {

        if (isValidEmail(userEmail)) {

            Optional<User> optionalUser = userMapper.getUserByEmail(userEmail);

            if (optionalUser.isPresent()) {

                Optional<Account> optionalAccount = accountMapper.getAccountByUserId(optionalUser.get().getId());

                if (optionalAccount.isPresent())
                    return optionalAccount.get();
                else
                    throw new AccountNotFoundException("There is no Account with that userId");
            } else
                throw new UserNotFoundException("There is no User with that email");
        } else
            throw new CreateAccountException("Invalid email provided!");
    }

    public Account createAccount(User user, String password) {

        if (isValidEmail(user.getEmail())) {

            Optional<User> optionalUser = userMapper.getUserByEmail(user.getEmail());

            if (optionalUser.isEmpty()) {
                userMapper.createUser(user);
                System.out.println("user ID: " + user.getId());
            }

            if (optionalUser.isPresent()) {
                user = optionalUser.get();
            }

            Optional<Account> optionalAccount = accountMapper.getAccountByUserId(user.getId());

            if (optionalAccount.isEmpty()) {

                Account account = new Account();
                account.setUser(user);
                account.setPassword(password);

                accountMapper.createAccount(account);

                System.out.println("account ID: " + account.getId());

                return account;

            } else
                throw new AccountAlreadyExistsException("An Account with the email: '" + optionalAccount.get().getUser().getEmail() + "' already exists!");
        } else
            throw new CreateAccountException("Invalid email provided!");
    }
}
