package com.dancestudio.erp.entry;


import com.dancestudio.erp.enums.MembershipStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class StudentEntry {

    private Long studentId;
    private String name;
    private String email;
    private String phone;
    private String imageUrl;
    private MembershipStatus membershipStatus;
    private Long studioId;
    private List<StudentActivityAssignmentEntry> enrolledActivities;

    @JsonIgnore
    public List<Long> getEnrolledActivityIds() {
        List<Long> activityIds = new ArrayList<>();

        if (enrolledActivities != null) {
            for (StudentActivityAssignmentEntry activity : enrolledActivities) {
                if (activity != null) {
                    activityIds.add(activity.getAssignmentId());
                }
            }
        }

        return activityIds;
    }

}
