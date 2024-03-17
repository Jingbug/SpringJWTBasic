package com.example.springjwtbasic.service.impl;

import com.example.springjwtbasic.domain.User;
import com.example.springjwtbasic.dto.JoinDTO;
import com.example.springjwtbasic.repository.UserRepository;
import com.example.springjwtbasic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public boolean joinProcess(JoinDTO joinDTO) {

        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();

        boolean existsByUsername = userRepository.existsByUsername(username);

        if (existsByUsername) {
            return false;
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(bCryptPasswordEncoder.encode(password));
        user.setRole("ROLE_ADMIN");

        userRepository.save(user);

        return true;
    }
}
