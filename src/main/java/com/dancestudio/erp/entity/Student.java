package com.dancestudio.erp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@Table(name = "student", uniqueConstraints = {
        @UniqueConstraint(name = "name_email_key", columnNames = {"name", "email"})
})
public class Student extends BaseEntity {

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    private String profileImage;

    @Column(name = "enrolled_activity_ids")
    private String enrolledActivityIds;

    @Column(name = "status", nullable = false)
    private String status;

    @Column(name = "studio_id", nullable = false)
    private Long studioId;


    @JsonIgnore
    public List<Long> getEnrolledActivityIdList() {
        return Objects.nonNull(enrolledActivityIds) ?
                List.of(enrolledActivityIds.split(",")).stream().map(Long::valueOf).collect(Collectors.toList()) : List.of();
    }

    @JsonIgnore
    public void setEnrolledActivityIds(List<Long> ids) {
        this.enrolledActivityIds = String.join(",", ids.stream().map(String::valueOf).collect(Collectors.toList()));
    }

    @JsonIgnore
    public void addEnrolledActivityId(Long id) {
        List<Long> ids = new ArrayList<>(getEnrolledActivityIdList());
        if (!ids.contains(id)) {
            ids.add(id);
        }

        setEnrolledActivityIds(ids);
    }
}
