package com.school.authservice.boot;

import com.school.authservice.entities.Role;
import com.school.authservice.entities.RoleEnum;
import com.school.authservice.respositories.RoleRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class RoleSeeder implements ApplicationListener<ContextRefreshedEvent > {

    private final RoleRepository roleRepository;

    public RoleSeeder( RoleRepository roleRepository){
        this.roleRepository=roleRepository;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent){
        this.loadRoles();
    }

    public void loadRoles(){
        RoleEnum[] roleNames = new RoleEnum[] {
                RoleEnum.SUPER_ADMIN,RoleEnum.ADMIN,RoleEnum.TEACHER,RoleEnum.STUDENT  };
        Map<RoleEnum,String> roleDescription= Map.of(
                RoleEnum.SUPER_ADMIN, "Super Administrator role",
                RoleEnum.ADMIN, "Administrator role",
                RoleEnum.TEACHER, "Teacher role",
                RoleEnum.STUDENT, "Student role"

        );
        Arrays.stream(roleNames).forEach((roleName)->{
            Optional<Role> optionalRole=roleRepository.findByName(roleName);

            optionalRole.ifPresentOrElse(System.out::println,()->{
                Role roleToCreate=new Role();
                roleToCreate.setName(roleName);
                roleToCreate.setDescription(roleDescription.get(roleName));
                roleRepository.save(roleToCreate);

            });

        });
    }
}
