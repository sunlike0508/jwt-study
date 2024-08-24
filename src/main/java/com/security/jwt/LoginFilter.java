package com.security.jwt;


import java.util.Collection;
import java.util.Iterator;
import com.security.service.CustomUserDetails;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@RequiredArgsConstructor
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {

        String username = obtainUsername(request);
        String password = obtainPassword(request);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(username, password, null);

        return authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }


    // 여기서 JWT 발급
    @Override
    public void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authentication) {
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

        String username = customUserDetails.getUsername();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();

        GrantedAuthority authority = iterator.next();

        String role = authority.getAuthority();

        String token = jwtUtil.createJwt(username, role, 60 * 60 * 1000L);

        response.addHeader("Authorization", "Bearer " + token);
    }


    // 실패시 실행되는 메소드
    @Override
    public void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authenticationException) {

        response.setStatus(401);
    }
}
