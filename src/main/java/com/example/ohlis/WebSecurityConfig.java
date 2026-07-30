package com.example.ohlis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import lombok.extern.slf4j.Slf4j;

import java.lang.SuppressWarnings;

@Slf4j
@Configuration
@EnableWebSecurity
class WebSecurityConfig {

  @Bean
  @Profile("!disableAuthentication")
  @SuppressWarnings("null")
  SecurityFilterChain securityFilterChain(HttpSecurity http) {
    http
        .authorizeHttpRequests((authorize) -> authorize
            .anyRequest().authenticated())
        .formLogin((form) -> form
            .loginPage("/login")
            .permitAll())
        .logout(LogoutConfigurer::permitAll);

    return http.build();
  }

  @Bean
  @Profile("disableAuthentication")
  SecurityFilterChain testSecurityFilterChain(HttpSecurity http) {
    log.warn("This test profile disables authentication!");
    http
        .authorizeHttpRequests((authorize) -> authorize
            .anyRequest().permitAll());

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  UserDetailsService userDetailsService(PasswordEncoder encoder) {
    String password = encoder.encode("password");
    UserDetails user = User.withUsername("user").password(password).roles("USER").build();
    return new InMemoryUserDetailsManager(user);
  }
}