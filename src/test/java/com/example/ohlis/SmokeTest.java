package com.example.ohlis;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SmokeTest {

  @Autowired
  private LegislatorsController legislatorsController;

  @Autowired
  private LegislationController legislationController;

  @Test
  void contextLoads() throws Exception {
    assertThat(legislatorsController).isNotNull();
    assertThat(legislationController).isNotNull();
  }
}
