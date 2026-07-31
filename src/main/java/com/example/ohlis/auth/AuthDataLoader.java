package com.example.ohlis.auth;

import java.util.Arrays;
import java.util.Collection;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class AuthDataLoader implements
    ApplicationListener<ContextRefreshedEvent> {

  boolean alreadySetup = false;

  private UserRepository userRepository;

  private RoleRepository roleRepository;

  private final PasswordEncoder passwordEncoder;

  AuthDataLoader(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.roleRepository = roleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  @Transactional
  public void onApplicationEvent(ContextRefreshedEvent event) {

    if (alreadySetup)
      return;

    Role userRole = createRoleIfNotFound("ROLE_USER");
    Role adminRole = createRoleIfNotFound("ROLE_ADMIN");

    User user = new User();
    user.setUserName("user");
    user.setPassword(passwordEncoder.encode("user"));
    user.setRoles((Collection<Role>) Arrays.asList(userRole));
    userRepository.save(user);

    User admin = new User();
    admin.setUserName("admin");
    admin.setPassword(passwordEncoder.encode("admin"));
    admin.setRoles((Collection<Role>) Arrays.asList(adminRole));
    userRepository.save(admin);
    alreadySetup = true;
  }

  @Transactional
  Role createRoleIfNotFound(String name) {
    Role role = roleRepository.findByName(name);
    if (role == null) {
      role = new Role();
      role.setName(name);
      roleRepository.save(role);
    }
    return role;
  }
}