package com.example.demo.repository;

import com.example.demo.model.Role;
import com.example.demo.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<Users, Long> {
    Page<Users> findAll(Pageable pageable);

    Users findByUsername(String username);

    Users findByEmail(String email);

    Users getById(Long id);

    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    Users save(Users user);

    void deleteById(Long id);

    List<Users> findAllByRole(Role role);
}
