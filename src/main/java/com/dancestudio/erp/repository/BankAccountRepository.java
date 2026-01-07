package com.dancestudio.erp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.dancestudio.erp.modules.member.BankAccount;

import java.util.Optional;

public interface BankAccountRepository extends JpaRepository<BankAccount, Long> {

    Optional<BankAccount> findByInstructorId(Long instructorId);

}
