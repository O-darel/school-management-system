package com.school.authservice.respositories;

import com.school.authservice.entities.Role;
import com.school.authservice.entities.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role ,Integer> {

    //find by role name
    Optional<Role> findByName(RoleEnum role);
}
