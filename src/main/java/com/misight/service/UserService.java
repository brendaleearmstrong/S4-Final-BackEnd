package com.misight.service;

import com.misight.model.User;
import com.misight.model.Privileges;
import com.misight.repository.UserRepo;
import com.misight.repository.PrivilegesRepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Transactional
public class UserService {
    private final UserRepo userRepository;
    private final PrivilegesRepo privilegesRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepository, PrivilegesRepo privilegesRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.privilegesRepository = privilegesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsernameWithPrivileges(username);
    }

    public User createUser(String username, String password, Set<String> privilegeNames) {
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));

        if (privilegeNames != null && !privilegeNames.isEmpty()) {
            Set<Privileges> privileges = privilegesRepository.findByNameIn(privilegeNames);
            user.setPrivileges(privileges);
        }

        return userRepository.save(user);
    }

    public Optional<User> updateUser(Long id, String username, String password, Set<String> privilegeNames) {
        return userRepository.findById(id)
                .map(user -> {
                    if (!user.getUsername().equals(username) && userRepository.existsByUsername(username)) {
                        throw new IllegalArgumentException("Username already exists");
                    }

                    user.setUsername(username);
                    if (password != null && !password.isEmpty()) {
                        user.setPassword(passwordEncoder.encode(password));
                    }

                    if (privilegeNames != null && !privilegeNames.isEmpty()) {
                        Set<Privileges> privileges = privilegesRepository.findByNameIn(privilegeNames);
                        user.setPrivileges(privileges);
                    }

                    return userRepository.save(user);
                });
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}