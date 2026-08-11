package com.dancestudio.erp.modules.member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

        Optional<Member> findByNameAndMemberTypeAndEmail(String name, String memberType, String email);

        @Query("SELECT s FROM Member s WHERE s.branch.id = :branchId AND s.memberType = 'STUDENT' AND (:activityName IS NULL OR :activityName = '' OR s.id IN (SELECT sa.student.id FROM StudentActivityAssignment sa WHERE sa.activityName = :activityName AND "
                        + "((:status = 'ACTIVE' AND sa.membershipEndDate >= CURRENT_DATE) OR (:status = 'INACTIVE' AND sa.membershipEndDate < CURRENT_DATE)))) " // This line is from the original query, kept for context of the previous fix
                        + "AND (:searchTerm IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(s.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%')))") // This line is from the original query, kept for context of the previous fix
        List<Member> findAllStudentsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(
                        @Param("branchId") Long branchId, @Param("activityName") String activityName,
                        @Param("status") String status, @Param("searchTerm") String searchTerm);

        @Query("""
                        SELECT s FROM Member s
                        LEFT JOIN MemberActiveStatus mas ON s.id = mas.id
                        WHERE s.branch.id = :branchId AND s.memberType = 'STUDENT'
                        AND (:activityName IS NULL OR :activityName = '' OR s.id IN (SELECT sa.student.id FROM StudentActivityAssignment sa WHERE sa.activityName = :activityName))
                        AND ( (:status IS NULL) OR (:status = 'ACTIVE' AND EXISTS (SELECT 1 FROM ActivePeriod ap WHERE ap.memberActiveStatus.id = s.id AND CURRENT_DATE >= ap.startDate AND (ap.endDate IS NULL OR CURRENT_DATE <= ap.endDate))) OR (:status = 'INACTIVE' AND NOT EXISTS (SELECT 1 FROM ActivePeriod ap WHERE ap.memberActiveStatus.id = s.id AND CURRENT_DATE >= ap.startDate AND (ap.endDate IS NULL OR CURRENT_DATE <= ap.endDate))) )
                        AND (:searchTerm IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(s.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
                        AND (:isActive IS NULL OR s.isActive = :isActive)
                        """)
        Page<Member> findAllStudentsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTermAndIsActive(
                        @Param("branchId") Long branchId, @Param("activityName") String activityName,
                        @Param("status") String status, Pageable pageable, @Param("searchTerm") String searchTerm,
                        @Param("isActive") Boolean isActive);

        @Query("""
                        SELECT i FROM Member i
                        LEFT JOIN MemberActiveStatus mas ON i.id = mas.id
                        WHERE i.branch.id = :branchId AND i.memberType = 'INSTRUCTOR'
                        AND (:activityName IS NULL OR :activityName = '' OR i.id IN (SELECT sa.instructor.id FROM InstructorActivityAssignment sa WHERE sa.activityName = :activityName))
                        AND ( (:status IS NULL) OR (:status = 'ACTIVE' AND EXISTS (SELECT 1 FROM ActivePeriod ap WHERE ap.memberActiveStatus.id = i.id AND CURRENT_DATE >= ap.startDate AND (ap.endDate IS NULL OR CURRENT_DATE <= ap.endDate))) OR (:status = 'INACTIVE' AND NOT EXISTS (SELECT 1 FROM ActivePeriod ap WHERE ap.memberActiveStatus.id = i.id AND CURRENT_DATE >= ap.startDate AND (ap.endDate IS NULL OR CURRENT_DATE <= ap.endDate))) )
                        AND (:searchTerm IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(i.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
                        """)
        List<Member> findAllInstructorsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(
                        @Param("branchId") Long branchId, @Param("activityName") String activityName,
                        @Param("status") String status, @Param("searchTerm") String searchTerm);

        @Query("""
                        SELECT i FROM Member i
                        LEFT JOIN MemberActiveStatus mas ON i.id = mas.id
                        WHERE i.branch.id = :branchId AND i.memberType = 'INSTRUCTOR'
                        AND (:activityName IS NULL OR :activityName = '' OR i.id IN (SELECT sa.instructor.id FROM InstructorActivityAssignment sa WHERE sa.activityName = :activityName))
                        AND ( (:status IS NULL) OR (:status = 'ACTIVE' AND EXISTS (SELECT 1 FROM ActivePeriod ap WHERE ap.memberActiveStatus.id = i.id AND CURRENT_DATE >= ap.startDate AND (ap.endDate IS NULL OR CURRENT_DATE <= ap.endDate))) OR (:status = 'INACTIVE' AND NOT EXISTS (SELECT 1 FROM ActivePeriod ap WHERE ap.memberActiveStatus.id = i.id AND CURRENT_DATE >= ap.startDate AND (ap.endDate IS NULL OR CURRENT_DATE <= ap.endDate))) )
                        AND (:searchTerm IS NULL OR LOWER(i.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(i.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%')))
                        """)
        Page<Member> findAllInstructorsByBranchIdAndOptionalActivityIdAndOptionalStatusAndSearchTerm(
                        @Param("branchId") Long branchId, @Param("activityName") String activityName,
                        @Param("status") String status, Pageable pageable,
                        @Param("searchTerm") String searchTerm);

        @Query("SELECT COUNT(s) FROM Member s WHERE s.branch.id = :branchId and s.memberType = 'STUDENT'")
        long totalStudentsByBranchId(@Param("branchId") Long branchId);

        @Query("SELECT s FROM Member s WHERE s.branch.id = :branchId")
        List<Member> findByBranchId(@Param("branchId") Long branchId);

        @Query("SELECT COUNT(s) FROM Member s WHERE s.branch.id = :branchId and s.memberType = 'INSTRUCTOR'")
        long totalInstructorsByBranchId(@Param("branchId") Long branchId);

        List<Member> findByBranchIdAndMemberType(Long branchId, String memberType);

        @Query("SELECT m FROM Member m WHERE m.branch.id = :branchId and FUNCTION('MONTH', m.dob) = :month AND FUNCTION('DAY', m.dob) = :day")
        Page<Member> findByDobMonthDay(@Param("month") int month, @Param("day") int day,
                        @Param("branchId") Long branchId, Pageable pageable);

        @Query("""
                            SELECT DISTINCT m
                            FROM Member m
                            JOIN MemberActiveStatus mas ON m.id = mas.id
                            JOIN mas.activePeriods ap
                            WHERE m.branch.id = :branchId
                              AND (:memberType IS NULL OR m.memberType = :memberType)
                              AND CURRENT_DATE >= ap.startDate AND (ap.endDate IS NULL OR CURRENT_DATE <= ap.endDate)
                        """)
        List<Member> findActiveMembersByBranchId(
                        @Param("branchId") Long branchId,
                        @Param("memberType") String memberType);
}