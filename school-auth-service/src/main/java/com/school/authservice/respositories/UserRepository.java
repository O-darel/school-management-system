package com.school.authservice.respositories;

import com.school.authservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>{
    //find user by email
    Optional<User> findByEmail(String email);

    //find by id
    Optional<User> findById(Long id);
}
