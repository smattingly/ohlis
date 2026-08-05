package com.example.ohlis.legislation;

import com.example.ohlis.legislators.Legislator;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.Arrays;
import java.util.HashSet;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Legislation {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Getter
  @Setter
  private Long id;

  @Column(nullable = false)
  @NotBlank
  @Getter
  @Setter
  private String title;

  @Column(nullable = false)
  @NotBlank
  @Getter
  @Setter
  private String text;

  @ManyToMany
  @JoinTable(name = "legislation_sponsors", joinColumns = @JoinColumn(name = "legislation_id"), inverseJoinColumns = @JoinColumn(name = "legislator_id"))
  @Getter
  @Setter
  Set<Legislator> sponsors = new HashSet<Legislator>();

  protected Legislation() {
  }

  public Legislation(String title, String text, Set<Legislator> sponsors) {
    this.title = title;
    this.text = text;
    this.sponsors = sponsors;
  }

  @Override
  public String toString() {
    return String.format("Legislation id: %d, title: %s, text: %s", id, title, text.substring(0, 25));
  }

  public String getSponsorsString() {
    String[] result = new String[sponsors.size()];
    int i = 0;
    for (Legislator sponsor : sponsors) {
      result[i++] = sponsor.toString();
    }
    Arrays.sort(result);
    return Arrays.stream(result).collect(Collectors.joining(", "));
  }
}
