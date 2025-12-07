package com.dancestudio.erp.modules.member.instructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

import com.dancestudio.erp.response.AbstractResponse;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstructorActivityAssignmentResponse extends AbstractResponse {
    private List<InstructorActivityAssignmentEntry> data;
}