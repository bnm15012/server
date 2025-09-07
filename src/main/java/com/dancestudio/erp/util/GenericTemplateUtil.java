package com.dancestudio.erp.util;

import java.util.HashMap;
import java.util.Map;

import com.dancestudio.erp.entity.Branch;
import com.dancestudio.erp.entity.Member;
import com.dancestudio.erp.entity.Studio;

public class GenericTemplateUtil {

    public static String generateContentString(
            String content,
            Studio studio,
            Branch branch,
            Member member) {

        Map<String, String> variables = new HashMap<>();

        if (studio != null) {
            variables.put("studio_studioName", safe(studio.getName()));
            variables.put("studio_location", safe(studio.getLocation()));
            variables.put("studio_email", safe(studio.getEmail()));
            variables.put("studio_contactDetails", safe(studio.getContactDetails()));
        }

        if (branch != null) {
            variables.put("branch_name", safe(branch.getName()));
            variables.put("branch_address", safe(branch.getAddress()));
            variables.put("branch_city", safe(branch.getCity()));
            variables.put("branch_state", safe(branch.getState()));
            variables.put("branch_pincode", safe(branch.getPincode()));
            variables.put("branch_phone", safe(branch.getPhone()));
        }

        if (member != null) {
            variables.put("instructor_name", safe(member.getName()));
            variables.put("instructor_email", safe(member.getEmail()));
            variables.put("instructor_phone", safe(member.getPhone()));
            variables.put("instructor_dob", safe(member.getDob()));
            variables.put("instructor_address", safe(member.getAddress()));
            variables.put("instructor_emergencyContactNumber", safe(member.getEmergencyContactNumber()));

            variables.put("student_name", safe(member.getName()));
            variables.put("student_email", safe(member.getEmail()));
            variables.put("student_phone", safe(member.getPhone()));
            variables.put("student_dob", safe(member.getDob()));
            variables.put("student_address", safe(member.getAddress()));
            variables.put("student_emergencyContactNumber", safe(member.getEmergencyContactNumber()));

        }

        for (Map.Entry<String, String> entry : variables.entrySet()) {
            content = content.replace("{{" + entry.getKey() + "}}", entry.getValue());
        }

        // content += "\n\nPowered by Book & Manage!";

        return content;
    }

    private static String safe(Object value) {
        return value != null ? value.toString() : "";
    }
}
