package com.example.ohlis.legislators;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class LegislatorDto {

  @NotBlank
  @Getter
  @Setter
  private String firstName;

  @NotBlank
  @Getter
  @Setter
  private String lastName;

  @NotBlank
  @Getter
  @Setter
  private String hometown;
}
