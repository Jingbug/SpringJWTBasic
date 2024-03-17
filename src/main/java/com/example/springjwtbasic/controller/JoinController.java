package com.example.springjwtbasic.controller;

import com.example.springjwtbasic.dto.JoinDTO;
import com.example.springjwtbasic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {

    private final UserService userService;

    @PostMapping("/join")
    public String joinProcess(JoinDTO joinDTO) {
        boolean result = userService.joinProcess(joinDTO);

        if (result) {
            return "ok";
        } else {
            return "fail";
        }
    }
}
