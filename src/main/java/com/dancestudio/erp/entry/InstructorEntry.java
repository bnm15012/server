package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.MembershipStatus;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class InstructorEntry {

    private Long instructorId;
    private String name;
    private String email;
    private String phone;
    private Date dob;
    private String imageUrl;
    private String address;
    private String emergencyContactNumber;
    private MembershipStatus instructorStatus;
    private BankAccountEntry bankAccountDetails;
    private BranchEntry branchEntry;
    private StudioEntry studioEntry;

    private List<InstructorActivityAssignmentEntry> assignments;


    @JsonIgnore
    public List<Long> getAssignedActivityIds() {
        List<Long> activityIds = new ArrayList<>();

        if (assignments != null) {
            for (InstructorActivityAssignmentEntry activity : assignments) {
                if (activity != null) {
                    activityIds.add(activity.getAssignmentId());
                }
            }
        }

        return activityIds;
    }
}
