package com.example.configservice.repositories;
import com.example.configservice.entities.Stream;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StreamRepository extends JpaRepository<Stream,Integer> {

    //Optional<Streams> findById(Integer id);

    Optional<Stream> findByName(String name);
}
