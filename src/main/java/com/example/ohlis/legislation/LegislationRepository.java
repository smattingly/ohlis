package com.example.ohlis.legislation;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

public interface LegislationRepository extends CrudRepository<Legislation, Long> {
  List<Legislation> findAll();

  Optional<Legislation> findById(Long id);
}
