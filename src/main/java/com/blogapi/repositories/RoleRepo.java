package com.blogapi.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blogapi.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Long> {

}
