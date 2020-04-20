package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.User;
import se.expleostockholm.signup.exception.UserAlreadyExistsException;
import se.expleostockholm.signup.exception.UserNotFoundException;
import se.expleostockholm.signup.exception.CreateUserException;
import se.expleostockholm.signup.repository.UserMapper;

import java.util.List;
import java.util.Optional;

import static se.expleostockholm.signup.service.ServiceUtil.isValidEmail;

@Service
@AllArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    public List<User> getAllUsers() {
        return userMapper.getAllUsers();
    }

    public User createUser(User user) {

        if (isValidEmail(user.getEmail())) {
            Optional<User> optionalUser = userMapper.getUserByEmail(user.getEmail());

            if (optionalUser.isEmpty()) {
                userMapper.createUser(user);

                System.out.println("user ID: " + user.getId());

                return user;
            } else
                throw new UserAlreadyExistsException("A User with the email: '" + optionalUser.get().getEmail() + "' already exists!");
        } else
        throw new CreateUserException("Invalid email provided!");
    }

    public User getUserById(Long id) {
        return userMapper.getUserById(id).orElseThrow(() -> new UserNotFoundException("No user found!"));
    }

    public User getUserByEmail(String email) {
        if (isValidEmail(email))
            return userMapper.getUserByEmail(email).orElseThrow(() -> new UserNotFoundException("No user found!"));
        else
            throw new UserNotFoundException("Invalid email provided");
    }

    public boolean doesUserExist(User user) {

        return user.getId().equals(userMapper.getUserById(user.getId()));
    }
}