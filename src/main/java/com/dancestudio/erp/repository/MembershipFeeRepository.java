package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.MembershipFee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MembershipFeeRepository extends JpaRepository<MembershipFee, Long> {

    List<MembershipFee> findByStudioId(Long studioId);

}
