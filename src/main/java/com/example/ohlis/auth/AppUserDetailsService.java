package com.example.ohlis.auth;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("userDetailsService")
@Transactional
public class AppUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  AppUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String userName)
      throws UsernameNotFoundException {

    List<GrantedAuthority> userPermissions = new ArrayList<GrantedAuthority>();

    User user = userRepository.findByUserName(userName);
    if (user == null) {
      return new org.springframework.security.core.userdetails.User(
          " ", " ", true, true, true, true, userPermissions);
    }

    for (Role role : user.getRoles()) {
      userPermissions.add(new SimpleGrantedAuthority(role.getName()));
    }

    return new org.springframework.security.core.userdetails.User(
        user.getUserName(), user.getPassword(), true, true, true,
        true, userPermissions);
  }
}