package com.example.ohlis;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LegislatorRepository extends CrudRepository<Legislator, Long> {
  List<Legislator> findAll();
}
