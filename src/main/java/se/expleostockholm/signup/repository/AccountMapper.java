package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.Account;
import se.expleostockholm.signup.domain.Event;
import se.expleostockholm.signup.domain.User;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface AccountMapper {

    @Select("SELECT * FROM account_")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getUserById"))
    })
    List<Account> getAccounts();

    @Select("SELECT * FROM account_ WHERE id = #{id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getUserById"))
    })
    Optional<Account> getAccountById(Long id);

    @Select("SELECT * FROM account_ WHERE user_id = #{user_id}")
    @Results({
            @Result(property = "id", column = "id"),
            @Result(property = "user", column = "user_id",
                    one = @One(select = "se.expleostockholm.signup.repository.UserMapper.getUserById"))
    })
    Optional<Account> getAccountByUserId(Long user_id);

    @Insert("INSERT INTO account_ (user_id, password) VALUES (#{user.id}, #{password})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void createAccount(Account account);
}
