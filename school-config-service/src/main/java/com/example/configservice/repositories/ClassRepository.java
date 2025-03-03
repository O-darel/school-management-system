package com.example.configservice.repositories;

import com.example.configservice.entities.Class;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ClassRepository extends JpaRepository<Class,Integer> {

    //Optional<Classes> findById(Integer id);

    Optional<Class> findByName(String name);
}
