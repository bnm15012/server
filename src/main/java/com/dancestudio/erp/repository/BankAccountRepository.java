package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByInstructorId(Long instructorId);

}
