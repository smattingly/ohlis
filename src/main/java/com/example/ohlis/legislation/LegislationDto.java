package com.example.ohlis.legislation;

import java.util.List;

import com.example.ohlis.legislators.Legislator;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
public class LegislationDto {

  @NotBlank
  @Getter
  @Setter
  private String title;

  @NotBlank
  @Getter
  @Setter
  private String text;

  @Getter
  @Setter
  private List<Legislator> sponsors;
}
