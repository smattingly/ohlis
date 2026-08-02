package com.example.ohlis.legislation;

import com.example.ohlis.legislators.Legislator;

import java.util.List;
import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Getter;

@Entity
public class Legislation {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Getter
  private Long id;

  @Column(nullable = false)
  @Getter
  private String title;

  @Column(nullable = false)
  @Getter
  private String text;

  @ManyToMany
  @JoinTable(name = "legislation_sponsors", joinColumns = @JoinColumn(name = "legislation_id"), inverseJoinColumns = @JoinColumn(name = "legislator_id"))
  @Getter
  List<Legislator> sponsors = new ArrayList<Legislator>();

  protected Legislation() {
  }

  public Legislation(String title, String text, List<Legislator> sponsors) {
    this.title = title;
    this.text = text;
    this.sponsors = sponsors;
  }

  @Override
  public String toString() {
    return String.format("Legislation id: %d, title: %s, text: %s", id, title, text.substring(0, 25));
  }
}
