package security.jwt.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * 비밀번호를 암호화하는 메서드.
     */
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public SecurityFilterChain filter(HttpSecurity httpSecurity) throws Exception {

        // csrf
        httpSecurity.csrf((auth) -> auth.disable());

        // From 로그인 방식
        httpSecurity.formLogin((auth) -> auth.disable());

        // http basic
        httpSecurity.httpBasic((auth) -> auth.disable());

        httpSecurity.authorizeHttpRequests(
                (auth) -> auth.requestMatchers("/login", "/", "join").permitAll().requestMatchers("/admin")
                        .hasRole("ADMIN").anyRequest().authenticated());

        // 세션 설정 : JWT를 통한 인증/인가를 위해서 세션을 stateless 상태로 설정
        httpSecurity.sessionManagement((session) -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return httpSecurity.build();
    }
}
