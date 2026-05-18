package com.dancestudio.erp.modules.member.student.StudentActivityAssignment;

import com.dancestudio.erp.enums.ActivityType;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.util.DateUtil;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentActivityAssignmentSpecification {

    public static Specification<StudentActivityAssignment> getAssignmentsByCriteria(
            Long rootId,
            String rootType,
            ActivityType activityName,
            String searchText,
            java.time.LocalDate date) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            Join<StudentActivityAssignment, Member> studentJoin = root.join("student");

            if ("BRANCH".equalsIgnoreCase(rootType)) {
                Date now = date != null ? DateUtil.getUTCDate(date) : DateUtil.getCurrentDateUTC();
                if (activityName != null) {
                    predicates.add(criteriaBuilder.equal(root.get("activityName"), activityName));
                }
                if (StringUtils.hasText(searchText)) {
                    predicates.add(criteriaBuilder.like(root.get("batchName"), searchText + "%"));
                }
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("membershipStartDate"), now));
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("membershipEndDate"), now));
                predicates.add(criteriaBuilder.equal(studentJoin.get("branch").get("id"), rootId));
            } else if ("STUDENT".equalsIgnoreCase(rootType)) {
                predicates.add(criteriaBuilder.equal(studentJoin.get("id"), rootId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
