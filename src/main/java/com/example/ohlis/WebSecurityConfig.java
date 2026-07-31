package com.example.ohlis;

import org.springframework.boot.security.autoconfigure.web.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
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
  private Environment env;

  WebSecurityConfig(Environment env) {
    this.env = env;
  }

  @Bean
  @SuppressWarnings("null")
  SecurityFilterChain securityFilterChain(HttpSecurity http) {
    if (env.containsProperty("toggle_h2_console_on")) {
      // Spring security will disrupt H2 console without these changes.
      log.warn("Modifying security to permit /h2-console access");
      http.authorizeHttpRequests((authorize) -> authorize
          .requestMatchers(PathRequest.toH2Console()).permitAll());
      http.csrf((config) -> config.disable());
      http.headers((config) -> config.frameOptions((options) -> options.disable()));
    } else {
      http.authorizeHttpRequests((authorize) -> authorize
          .requestMatchers(PathRequest.toH2Console()).denyAll());
    }

    if (env.containsProperty("toggle_authentication_off")) {
      log.warn("Disabling authentication!");
      http
          .authorizeHttpRequests((authorize) -> authorize
              .anyRequest().permitAll());
    } else {
      http
          .authorizeHttpRequests((authorize) -> authorize
              .requestMatchers("/legislators/**").hasRole("ADMIN")
              .requestMatchers("/legislation/**").hasAnyRole("ADMIN", "USER")
              .anyRequest().authenticated())
          .formLogin((form) -> form
              .loginPage("/login")
              .permitAll())
          .logout(LogoutConfigurer::permitAll);
    }

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }
}