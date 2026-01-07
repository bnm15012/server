package com.dancestudio.erp.modules.member.memberActiveStatus;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberActiveStatusRepository extends JpaRepository<MemberActiveStatus, Long> {
}
