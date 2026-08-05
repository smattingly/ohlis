package com.example.ohlis.legislators;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Legislator {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Getter
  @Setter
  private Long id;

  @Column(nullable = false)
  @NotBlank
  @Getter
  @Setter
  private String firstName;

  @Column(nullable = false)
  @NotBlank
  @Getter
  @Setter
  private String lastName;

  @Column(nullable = false)
  @NotBlank
  @Getter
  @Setter
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
    return String.format("%s %s", firstName, lastName);
  }
}
