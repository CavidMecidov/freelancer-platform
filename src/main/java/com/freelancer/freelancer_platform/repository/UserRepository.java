package com.freelancer.freelancer_platform.repository;

import com.freelancer.freelancer_platform.entity.User;
import com.freelancer.freelancer_platform.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findById(Long id);

    boolean existsByEmail(String email);

    @Query("SELECT u FROM User u " +
            "WHERE (:name IS NULL OR :name = '' OR LOWER(u.name) LIKE LOWER(CONCAT('%', :name, '%'))) " +
            "AND (:surname IS NULL OR :surname = '' OR LOWER(u.surname) LIKE LOWER(CONCAT('%', :surname, '%')))")
    Page<User> searchUsers(@Param("name") String name,
                           @Param("surname") String surname,
                           Pageable pageable);

    @Query("SELECT u FROM User u JOIN u.skills s WHERE u.role = :role AND u.isActive = :isActive AND s.name = :skillName")
    List<User> findByRoleAndIsActiveAndSkillName(
            @Param("role") Role role,
            @Param("isActive") Boolean isActive,
            @Param("skillName") String skillName
    );

};
