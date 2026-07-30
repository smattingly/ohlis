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
import java.util.ArrayList;
import java.util.List;

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
            .requestMatchers("/legislators/**").hasRole("ADMIN")
            .requestMatchers("/legislation/**").hasAnyRole("ADMIN", "USER")
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
    List<UserDetails> userDetails = new ArrayList<UserDetails>();
    userDetails.add(User.withUsername("user").password(encoder.encode("user")).roles("USER").build());
    userDetails.add(User.withUsername("admin").password(encoder.encode("admin")).roles("ADMIN").build());
    return new InMemoryUserDetailsManager(userDetails);
  }
}