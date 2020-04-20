package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.Account;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface AccountMapper {

    @Select("SELECT * FROM account_")
    @Results({
            @Result(property = "user", column = "user_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getUserById"))
    })
    List<Account> getAllAccounts();

    @Select("SELECT * FROM account_ WHERE id = #{id}")
    Optional<Account> getAccountById(Long id);

    @Select("SELECT * FROM account_ WHERE user_id = #{id}")
    Optional<Account> getAccountByUserId(Long id);

    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    @Insert("INSERT INTO account_ (user_id, password) VALUES (#{user.id}, #{password})")
    Long createAccount(Account account);
}