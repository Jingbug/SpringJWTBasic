package com.example.springjwtbasic.service.impl;

import com.example.springjwtbasic.domain.User;
import com.example.springjwtbasic.dto.CustomUserDetails;
import com.example.springjwtbasic.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDate = userRepository.findByUsername(username);
        if (userDate != null) {
            return new CustomUserDetails(userDate);
        }
        return null;
    }
}
