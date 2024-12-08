package com.dancestudio.erp.repository;
 
 
 import com.dancestudio.erp.entity.Instructor;
 import com.dancestudio.erp.enums.MembershipStatus;
 import org.springframework.data.domain.Page;
 import org.springframework.data.domain.Pageable;
 import org.springframework.data.jpa.repository.JpaRepository;
 import org.springframework.data.jpa.repository.Query;
 import org.springframework.data.repository.query.Param;
 
 import java.util.List;
 import java.util.Optional;
 
 public interface InstructorRepository extends JpaRepository<Instructor, Long> {
 
     Optional<Instructor> findByNameAndEmail(String name, String email);
 
     @Query("SELECT i FROM Instructor i WHERE i.studio.id = :studioId AND (:activityId IS NULL OR i.id IN " +
       "(SELECT sa.instructor.id FROM InstructorActivityAssignment sa WHERE sa.activity.id = :activityId AND" +
       "((:status = 'ACTIVE' AND sa.endDate >= CURRENT_DATE) OR (:status = 'INACTIVE' AND sa.endDate < CURRENT_DATE))))")
     List<Instructor> findAllByStudioIdAndOptionalActivityIdAndOptionalStatus(@Param("studioId") Long studioId, @Param("activityId") Long activityId,
                                                                             @Param("status") String status);
 
     @Query("SELECT s FROM Instructor s WHERE s.studio.id = :studioId AND (:activityId IS NULL OR s.id IN " +
       "(SELECT sa.instructor.id FROM InstructorActivityAssignment sa WHERE sa.activity.id = :activityId AND " +
       "((:status = 'ACTIVE' AND sa.endDate >= CURRENT_DATE) OR (:status = 'INACTIVE' AND sa.endDate < CURRENT_DATE))))")
     Page<Instructor> findAllByStudioIdAndOptionalActivityIdAndOptionalStatus(@Param("studioId") Long studioId, @Param("activityId") Long activityId,
                                                                             @Param("status") String membershipStatus, Pageable pageable);
 
 }
