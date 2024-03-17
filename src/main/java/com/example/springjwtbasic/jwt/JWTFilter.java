package com.example.springjwtbasic.jwt;

import com.example.springjwtbasic.domain.User;
import com.example.springjwtbasic.dto.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // request의 Header에서 Authorization의 값을 가져온다.
        String authorization = request.getHeader("Authorization");

        // token의 여부, 정규적인 형태로 생성된 토근인지 여부 확인
        if (authorization == null || !authorization.startsWith("Bearer ")) {
            System.out.println("token null");

            // 필터 종료 및 다음 필터로 request, response 넘겨주기
            filterChain.doFilter(request, response);

            // 메서드를 종료 시켜야 한다. 필수 !!!
            return;
        }

        String token = authorization.split(" ")[1];

        // token 소멸 시간 검증
        if (jwtUtil.isExpired(token)) {
            System.out.println("token expired");
            filterChain.doFilter(request, response);
            return;
        }

        String username = jwtUtil.getUsername(token);
        String role = jwtUtil.getRole(token);

        User user = new User();
        user.setUsername(username);
        user.setPassword("temp_password");
        user.setRole(role);

        CustomUserDetails customUserDetails = new CustomUserDetails(user);

        // SpringSecurity Authentication Token create
        Authentication authToken = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

        // session에 사용자 등록 -> 일시적으로 생성되었다 파괴되는 session
        SecurityContextHolder.getContext().setAuthentication(authToken);

        filterChain.doFilter(request, response);
    }
}
