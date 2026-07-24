package com.example.ohlis;

import java.util.Collection;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface LegislatorRepository extends CrudRepository<Legislator, Long> {
  List<Legislator> findAll();

  List<Legislator> findByIdIn(Collection<Long> ids);
}
