package com.security.jwt;

import java.io.IOException;
import com.security.entity.Role;
import com.security.entity.UserEntity;
import com.security.service.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@RequiredArgsConstructor
public class JWTFilter extends OncePerRequestFilter {

    private final JWTUtil jwtUtil;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authorization = request.getHeader("Authorization");

        if(StringUtils.hasText(authorization) && authorization.startsWith("Bearer ")) {

            String token = authorization.split(" ")[1];

            if(jwtUtil.isExpired(token)) {

                System.out.println("token 만료");

                filterChain.doFilter(request, response);

                return;
            }

            String username = jwtUtil.getUsername(token);
            String role = jwtUtil.getRole(token);

            System.out.println(username);
            System.out.println(role);

            UserEntity userEntity =
                    UserEntity.builder().username(username).password("temppaswwrod").role(Role.valueOf(role)).build();

            CustomUserDetails customUserDetails = new CustomUserDetails(userEntity);

            Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null,
                    customUserDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}
