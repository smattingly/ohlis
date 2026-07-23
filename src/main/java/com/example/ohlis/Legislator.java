package com.example.ohlis;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

@Entity
public class Legislator {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Getter
  private Long id;

  @Column(nullable = false)
  @Getter
  private String firstName;

  @Column(nullable = false)
  @Getter
  private String lastName;

  @Column(nullable = false)
  @Getter
  private String hometown;

  protected Legislator() {
  }

  public Legislator(String firstName, String lastName, String hometown) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.hometown = hometown;
  }

  @Override
  public String toString() {
    return String.format("Legislator id: %d, firstName: %s, lastName: %s, hometown: %s", id, firstName, lastName,
        hometown);
  }

  public String getFullName() {
    return String.format("%s %s", firstName, lastName);
  }
}
