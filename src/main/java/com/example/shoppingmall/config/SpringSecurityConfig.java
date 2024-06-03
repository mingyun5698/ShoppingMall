package com.example.shoppingmall.config;


import com.example.shoppingmall.jwt.JwtAuthFilter;
import com.example.shoppingmall.jwt.JwtUtil;
import com.example.shoppingmall.security.MemberRoleEnum;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SpringSecurityConfig {


    private final JwtUtil jwtUtil;





    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        // resources 자원 접근 허용
        return (web) -> web.ignoring()
                .requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        /* csrf 설정 해제. */
        http
                .csrf((auth) -> auth.disable());

        /*JWT 사용 위해 기존의 세션 방식 인증 해제*/
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        /* URL Mapping */
        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("api/login", "/api/mypage", "/api/signup", "/api/members/**", "/api/boards").permitAll()
                        .requestMatchers("/api/mypage").authenticated() // 모든 로그인한 사용자에게 허용
                        .requestMatchers("/api/adminButton").hasRole(MemberRoleEnum.ADMIN.toString()) // ADMIN만 허용
                        .anyRequest().authenticated()
                );

        http
                .addFilterBefore(new JwtAuthFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class);

        /*로그인 페이지를 /login으로 설정한다.
        1. 인증이 되지 않은 사용자가 permitAll()페이지가 아닌 페이지에 접근할 때 /login으로 강제 이동 시킨다.
        2. 이때의 인증은 위에 필터에 등록해 놓은 JWT 토큰의 유무(유효성 검증) 기준이다.*/
//        http
//                .formLogin(login -> login.loginPage("/login"));

        /*인가 (권한 인증) 실패 시 아래의 핸들러 작동 */
        http.
                exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedPage("/forbidden"));


        return http.build();
    }
}
