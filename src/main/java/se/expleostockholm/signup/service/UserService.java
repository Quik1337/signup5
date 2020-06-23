package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.Role;
import se.expleostockholm.signup.domain.User;
import se.expleostockholm.signup.repository.UserMapper;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class UserService {

    private PasswordEncoder passwordEncoder;

    private final UserMapper userMapper;

    public List<User> getUsers() {
        return userMapper.getUsers();
    }

    public User getUserById(Long id) throws Exception {
        return userMapper.getUserById(id).orElseThrow(() -> new Exception("No user found!"));
    }

    public void registerUser(User user) throws Exception {

        Optional<User> optionalUser = userMapper.getUserByEmail(user.getEmail());

        if (optionalUser.isEmpty()) {
            userMapper.createUser(user);
            optionalUser = userMapper.getUserByEmail(user.getEmail());
        }

        if (optionalUser.isPresent()) {
            if (optionalUser.get().getRole().equals(Role.REGISTERED)) {
                throw new Exception("A user is already registered with that email!");
            } else
                optionalUser.get().setLast_name(user.getLast_name());
                optionalUser.get().setFirst_name(user.getFirst_name());
                optionalUser.get().setPassword(passwordEncoder.encode(user.getPassword()));
                userMapper.registerUser(optionalUser.get());

        } else
            throw new Exception("Unable to create User.");
    }

}