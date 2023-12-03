package com.elevenparis.store.service;

import com.elevenparis.store.entity.User;
import com.elevenparis.store.repository.LoginRepository;
import com.elevenparis.store.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void cadastrarUsuario(User user) {
        String encryptedPassword = bCryptPasswordEncoder.encode(user.getPassword());
        User newUser = new User();
        newUser.setPassword(encryptedPassword);
        newUser.setRole(user.getRole());
        newUser.setUsername(user.getUsername());
        userRepository.save(newUser);
    }
}
