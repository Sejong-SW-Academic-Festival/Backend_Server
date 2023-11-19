package com.calcalcal.sejong_log.Backend_Server.global.config;

import com.calcalcal.sejong_log.Backend_Server.domain.user.repository.UserRepository;
import com.calcalcal.sejong_log.Backend_Server.global.filter.JwtAuthorizationFilter;
import com.calcalcal.sejong_log.Backend_Server.global.utils.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
public class SecurityConfig {
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;
    private final RedisTemplate<String, String> redisTemplate;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilter(corsConfig.corsFilter())
                .addFilterBefore(
                        new JwtAuthorizationFilter(userRepository, jwtUtils, redisTemplate),
                        BasicAuthenticationFilter.class
                )
                .authorizeHttpRequests((authorizeRequests) ->
                        authorizeRequests
                                .anyRequest().hasRole("USER")
                );

        return httpSecurity.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return web -> {
            web.ignoring()
                    .requestMatchers("/user/signup", "/user/login");
        };
    }
}
