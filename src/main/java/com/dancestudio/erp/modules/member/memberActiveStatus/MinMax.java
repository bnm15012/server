package com.dancestudio.erp.modules.member.memberActiveStatus;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MinMax {
    Date minDate;
    Date maxDate;
}
