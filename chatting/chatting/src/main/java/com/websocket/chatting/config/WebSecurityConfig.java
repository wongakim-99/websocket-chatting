package com.websocket.chatting.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager; // 이 부분이 중요합니다!
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // CSRF 비활성화
                .headers(headers -> headers.frameOptions(frameOptions -> frameOptions.sameOrigin())) // frameOptions 설정
                .formLogin(form -> form.permitAll()) // 로그인 페이지는 누구나 접근 가능하게 설정
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/","/chat/**").hasRole("USER") // /chat/**에 USER 권한 필요
                        .anyRequest().permitAll()); // 그 외 요청 허용

        return http.build();
    }

    /**
     * 테스트를 위해 In-Memory에 계정을 임의로 생성한다.
     * 서비스에 사용시에는 DB데이터를 이용하도록 수정이 필요하다.
     */
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user1 = User.withUsername("happydaddy")
                .password("{noop}1234")
                .roles("USER")
                .build();

        UserDetails user2 = User.withUsername("angrydaddy")
                .password("{noop}1234")
                .roles("USER")
                .build();

        UserDetails guest = User.withUsername("guest")
                .password("{noop}1234")
                .roles("GUEST")
                .build();

        return new InMemoryUserDetailsManager(user1, user2, guest);
    }

    // 비밀번호 인코더 설정 (테스트를 위해 NoOpPasswordEncoder 사용)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
