package com.bt.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(
                                "/", "/main", "/search", "/product/**","/product/*/review",  // ✅ 상세 페이지 허용
                                "/css/**", "/js/**", "/img/**",          // ✅ 정적 리소스 허용
                                "/admin", "/admin/**",                   // ✅ 관리자 페이지
                                "/login", "/logout"                     // ✅ 로그인/로그아웃
                        ).permitAll()
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/main", true)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/main")
                        .permitAll()
                )
                .csrf(csrf -> csrf.disable());  // ✅ POST 요청 막힘 방지 (필요 시 조심히 사용)

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
