package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByName(String userName);

    Optional<User> findByEmail(String email);

    List<User> findByBranchId(Long branchId);

    List<User> findByStudioId(Long studioId);

}