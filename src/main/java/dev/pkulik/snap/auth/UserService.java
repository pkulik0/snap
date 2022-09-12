package dev.pkulik.snap.auth;

import dev.pkulik.snap.generator.GeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService implements UserDetailsService {
    private final GeneratorService generatorService;
    private final  UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findUserByUsername(username);
        if(optionalUser.isEmpty()) throw new UsernameNotFoundException("User not found in the database.");

        User user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    Optional<User> createUser(User user) {
        user.setId(generatorService.getNextId(User.SEQUENCE_NAME));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.insert(user);
        return Optional.of(user);
    }
}
