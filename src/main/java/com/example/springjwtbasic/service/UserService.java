package com.example.springjwtbasic.service;

import com.example.springjwtbasic.dto.JoinDTO;

public interface UserService {
    boolean joinProcess(JoinDTO joinDTO);
}
