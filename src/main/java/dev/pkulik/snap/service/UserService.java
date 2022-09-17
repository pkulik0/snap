package dev.pkulik.snap.service;

import dev.pkulik.snap.model.User;
import dev.pkulik.snap.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
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
    private final SequenceService sequenceService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private static final int minPasswordLength = 8;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), new ArrayList<>());
    }

    public Optional<User> createUser(User user) {
        if(user.getPassword().length() < minPasswordLength)
            return Optional.empty();

        if(!EmailValidator.getInstance().isValid(user.getEmail()))
            return Optional.empty();

        user.setId(sequenceService.getNextId(User.SEQUENCE_NAME));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.save(user);
        return Optional.of(user);
    }
}
