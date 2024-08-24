package com.security.config;


import com.security.jwt.LoginFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;


    /**
     * 비밀번호를 암호화하는 메서드.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
            throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    public SecurityFilterChain filter(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.headers(AbstractHttpConfigurer::disable).httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable);

        httpSecurity.authorizeHttpRequests(
                (auth) -> auth.requestMatchers("/", "/login", "/join", "/join/new").permitAll()
                        .requestMatchers("/admin").hasRole("ADMIN").requestMatchers("/my/**")
                        .hasAnyRole("ADMIN", "USER").anyRequest().authenticated());

        httpSecurity.addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration)),
                UsernamePasswordAuthenticationFilter.class);


        // 세션 설정 : JWT를 통한 인증/인가를 위해서 세션을 stateless 상태로 설정
        httpSecurity.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }
}