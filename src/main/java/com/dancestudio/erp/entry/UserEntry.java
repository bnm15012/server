package com.dancestudio.erp.entry;

import com.dancestudio.erp.enums.UserType;
import lombok.Data;

@Data
public class UserEntry {

    private Long userId;
    private String userName;
    private String password;
    private String email;
    private String phone;
    private UserType role;
    private Boolean enabled;
    private StudioEntry studioEntry;

    private String token;

}
