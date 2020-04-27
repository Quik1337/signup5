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

}