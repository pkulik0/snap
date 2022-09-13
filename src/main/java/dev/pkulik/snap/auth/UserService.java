package dev.pkulik.snap.auth;

import dev.pkulik.snap.generator.GeneratorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
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
        User user = userRepository.findUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }

    Optional<User> createUser(User user) {
        if(!StringUtils.hasText(user.getUsername()) || !StringUtils.hasText(user.getEmail()))
            return Optional.empty();

        if(user.getPassword().length() < 8)
            return Optional.empty();

        user.setId(generatorService.getNextId(User.SEQUENCE_NAME));
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        userRepository.insert(user);
        return Optional.of(user);
    }
}
