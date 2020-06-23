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
    List<User> getUsers();

    @Select("SELECT * FROM user_ WHERE id = #{id}")
    Optional<User> getUserById(Long id);

    @Select("SELECT * FROM user_ WHERE email = #{email}")
    Optional<User> getUserByEmail(String email);

    @Insert("INSERT INTO user_ (email, first_name, last_name) VALUES (#{email}, #{first_name}, #{last_name})")
    @Options(useGeneratedKeys=true, keyProperty="id", keyColumn="id")
    void createUser(User guest);

    @Update("UPDATE user_ SET role = 'REGISTERED'::role_, password = #{password}, first_name = #{first_name}, last_name = #{last_name} WHERE email = #{email}")
    void registerUser(User user);
}