package se.expleostockholm.signup.repository;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import se.expleostockholm.signup.domain.User;

import java.util.List;
import java.util.Optional;

@Mapper
@Repository
public interface UserMapper {

    @Select("SELECT * FROM user_")
    List<User> getAllUsers();

    @Select("SELECT * FROM user_ WHERE id = #{user_id}")
    Optional<User> getUserById(Long userId);

    @Select("SELECT * FROM user_ WHERE email = #{email}")
    Optional<User> getUserByEmail(String email);

    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    @Insert("INSERT INTO user_ (email, first_name, last_name) VALUES (#{email}, #{first_name}, #{last_name})")
    Long createUser(User user);

    @Delete("DELETE FROM user_ WHERE email = #{email}")
    void removeUserByEmail(String email);
}
