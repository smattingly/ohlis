package com.example.ohlis;

import java.io.IOException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@Order(1)
public class ClientFeatureToggleFilter implements Filter {
  @Value("${spring.profiles.active: }")
  private String activeProfiles;

  /*
   * Implements a feature flag mechanism for client-side features.
   * Flags are set on server side by activating Spring profiles.
   * Server relays them to client as cookies.
   * Cookies are treated as booleans: true if defined, false if not.
   * (IOW, value is irrelevant.)
   */
  @Override
  public void doFilter(
      ServletRequest request,
      ServletResponse response,
      FilterChain chain) throws ServletException, IOException {

    // Profile/Cookie names with this prefix are treated as client feature flags.
    final String clientFlagPrefix = "toggle_client_";

    // We use session cookies, but even so server profiles may have changed during
    // this session. To be safe, remove all client feature flag cookies to start
    // with a clean state.
    Cookie[] requestCookies = ((HttpServletRequest) request).getCookies();
    if (requestCookies != null) {
      Arrays.stream(requestCookies)
          .map(cookie -> cookie.getName())
          .filter(name -> name.startsWith(clientFlagPrefix))
          .forEach(clientFlag -> {
            Cookie cookie = new Cookie(clientFlag, "1");
            cookie.setMaxAge(0); // remove cookie
            cookie.setPath("/");
            cookie.setDomain(request.getServerName());
            ((HttpServletResponse) response).addCookie(cookie);
          });
    }

    // Now set a cookie for each relevant active Spring profile.
    // We may have just deleted some of these, but no harm done.
    Arrays.stream(activeProfiles.split(","))
        .filter(profileName -> profileName.startsWith(clientFlagPrefix))
        .forEach(clientFlag -> {
          Cookie cookie = new Cookie(clientFlag, "1");
          cookie.setMaxAge(-1); // set session cookie
          cookie.setPath("/");
          cookie.setDomain(request.getServerName());
          ((HttpServletResponse) response).addCookie(cookie);
        });

    // Do the usual processing.
    chain.doFilter(request, response);
  }
}