package com.example.ohlis;

import java.io.IOException;

import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(1)
public class ClientFeatureToggleFilter implements Filter {
  private Environment env;
  private boolean hasRun = false;

  ClientFeatureToggleFilter(Environment env) {
    this.env = env;
  }

  @Override
  public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain) throws ServletException, IOException {

    if (!hasRun) {
      // Just do this once.
      hasRun = true;

      String[] clientSideFeatureFlags = { "toggle_client_validation_off" };

      // For each client side feature flag ...
      for (String flag : clientSideFeatureFlags) {
        Cookie cookie = new Cookie(flag, "1");

        if (env.containsProperty(flag)) {
          // ... if the server env defined it, pass value to client in a cookie ...
          String value = env.getProperty(flag);
          cookie.setValue(value);
          cookie.setMaxAge(-1);
          log.info("Setting session cookie for client feature flag {} to {}.", flag, value);
        } else {
          // ... otherwise delete cookie in case it was previously defined.
          cookie.setMaxAge(0);
        }
        cookie.setPath("/");
        cookie.setDomain(request.getServerName());
        ((HttpServletResponse) response).addCookie(cookie);
      }
    }

    // Do the usual processing.
    chain.doFilter(request, response);
  }
}