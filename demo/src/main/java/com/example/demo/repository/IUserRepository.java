package com.example.demo.repository;

import com.example.demo.model.Role;
import com.example.demo.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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

    @Query("SELECT u FROM Users u WHERE " +
            "(:keyword IS NULL OR LOWER(u.username) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.fullName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%'))) " +
            "AND (:role IS NULL OR u.role = :role)")
    Page<Users> searchByKeywordAndRole(@Param("keyword") String keyword, @Param("role") Role role, Pageable pageable);

}
