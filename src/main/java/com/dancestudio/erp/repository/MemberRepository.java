package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Member;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findByNameAndEmail(String name, String email);

    @Query("SELECT s FROM Member s WHERE s.branch.id = :branchId AND s.memberType = 'STUDENT' AND (:activityId IS NULL OR s.id IN (SELECT sa.student.id FROM StudentActivityAssignment sa WHERE sa.activity.id = :activityId AND " +
            "((:status = 'ACTIVE' AND sa.membershipEndDate >= CURRENT_DATE) OR (:status = 'INACTIVE' AND sa.membershipEndDate < CURRENT_DATE)))) " +
            "AND (:searchTerm IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Member> findAllStudentsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(@Param("branchId") Long branchId, @Param("activityId") Long activityId, @Param("status") String status, @Param("searchTerm") String searchTerm);

    @Query("SELECT s FROM Member s WHERE s.branch.id = :branchId AND s.memberType = 'STUDENT' AND (:activityId IS NULL OR s.id IN (SELECT sa.student.id FROM StudentActivityAssignment sa WHERE sa.activity.id = :activityId AND " +
            "((:status = 'ACTIVE' AND sa.membershipEndDate >= CURRENT_DATE) OR (:status = 'INACTIVE' AND sa.membershipEndDate < CURRENT_DATE)))) " +
            "AND (:searchTerm IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Member> findAllStudentsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(@Param("branchId") Long branchId, @Param("activityId") Long activityId, @Param("status") String status, Pageable pageable, @Param("searchTerm") String searchTerm);

    @Query("SELECT i FROM Member i WHERE i.branch.id = :branchId AND i.memberType = 'INSTRUCTOR' AND (:activityId IS NULL OR i.id IN (SELECT sa.instructor.id FROM InstructorActivityAssignment sa WHERE sa.activity.id = :activityId AND" +
            "((:status = 'ACTIVE' AND sa.endDate >= CURRENT_DATE) OR (:status = 'INACTIVE' AND sa.endDate < CURRENT_DATE)))) AND (:searchTerm IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    List<Member> findAllInstructorsByBranchIdAndOptionalActivityIdAndOptionalStatus(@Param("branchId") Long branchId, @Param("activityId") Long activityId, @Param("status") String status, @Param("searchTerm") String searchTerm);

    @Query("SELECT s FROM Member s WHERE s.branch.id = :branchId AND s.memberType = 'INSTRUCTOR' AND (:activityId IS NULL OR s.id IN (SELECT sa.instructor.id FROM InstructorActivityAssignment sa WHERE sa.activity.id = :activityId AND " +
            "((:status = 'ACTIVE' AND sa.endDate >= CURRENT_DATE) OR (:status = 'INACTIVE' AND sa.endDate < CURRENT_DATE)))) AND (:searchTerm IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')))")
    Page<Member> findAllInstructorsByBranchIdAndOptionalActivityIdAndOptionalStatus(@Param("branchId") Long branchId, @Param("activityId") Long activityId, @Param("status") String membershipStatus, Pageable pageable, @Param("searchTerm") String searchTerm);

    @Query("SELECT COUNT(s) FROM Member s WHERE s.branch.id = :branchId and s.memberType = 'STUDENT'")
    long totalStudentsByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT s FROM Member s WHERE s.branch.id = :branchId")
    List<Member> findByBranchId(@Param("branchId") Long branchId);

    @Query("SELECT COUNT(s) FROM Member s WHERE s.branch.id = :branchId and s.memberType = 'INSTRUCTOR'")
    long totalInstructorsByBranchId(@Param("branchId") Long branchId);


}