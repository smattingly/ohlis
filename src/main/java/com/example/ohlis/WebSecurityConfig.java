package com.example.ohlis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

import java.lang.SuppressWarnings;

@Slf4j
@Configuration
@EnableWebSecurity
class WebSecurityConfig {
  @Bean
  @Profile("disable-authentication")
  SecurityFilterChain withoutAuthentication(HttpSecurity http) {
    log.warn("Disabling authentication!");
    http.csrf((config) -> config.disable());
    http.authorizeHttpRequests((authorize) -> authorize
        .anyRequest().permitAll());
    return http.build();
  }

  @Bean
  @Profile("!disable-authentication")
  @SuppressWarnings("null")
  SecurityFilterChain withAuthentication(HttpSecurity http) {
    http
        .authorizeHttpRequests((authorize) -> authorize
            .requestMatchers("/legislators/**").hasRole("ADMIN")
            .requestMatchers("/legislation/**").hasAnyRole("ADMIN", "USER")
            .anyRequest().authenticated())
        .formLogin((form) -> form
            .loginPage("/login")
            .defaultSuccessUrl("/", true)
            .permitAll())
        .logout(LogoutConfigurer::permitAll);

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}