package se.expleostockholm.signup.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import se.expleostockholm.signup.domain.CustomUserDetails;
import se.expleostockholm.signup.domain.Role;
import se.expleostockholm.signup.domain.User;
import se.expleostockholm.signup.repository.UserMapper;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Optional<User> optionalUser = userMapper.getUserByEmail(email);

        return new CustomUserDetails(optionalUser.get()); //ustomUserDetails().springframework.security.core.userdetails.User(email, (optionalUser.get().getPassword()), new ArrayList<>());

        //passwordEncoder.encode(user.getPassword())

        /*
        Role role = optionalUser.get().getRole();

        Set<GrantedAuthority> authorities = new HashSet<>();

        authorities.add(new SimpleGrantedAuthority(role.toString()));

        CustomUserDetails customUserDetail = new CustomUserDetails();

        customUserDetail.setUser(optionalUser.get());
        customUserDetail.setAuthorities(authorities);

        return customUserDetail;
        */

        /*
        if (optionalUser.isPresent())
            return (UserDetails) optionalUser.get();
        else
            throw new UsernameNotFoundException("No user found");*/
    }
}
