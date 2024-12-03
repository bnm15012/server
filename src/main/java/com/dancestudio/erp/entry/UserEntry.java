package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.UserType;
import lombok.Data;

import java.util.Date;

@Data
public class UserEntry {

    private Long userId;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private String imageUrl;
    private UserType role;
    private Boolean enabled;
    private StudioEntry studioEntry;

    private Date membershipEndDate;
    private String token;

}
