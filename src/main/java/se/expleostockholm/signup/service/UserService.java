package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.User;
import se.expleostockholm.signup.repository.UserMapper;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public List<User> getUsers() {
        return userMapper.getUsers();
    }

    public User getUserById(Long id) throws Exception {
        return userMapper.getUserById(id).orElseThrow(() -> new Exception("No user found!"));
    }

}