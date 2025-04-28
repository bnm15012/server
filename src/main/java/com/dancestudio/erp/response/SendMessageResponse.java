package com.dancestudio.erp.response;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SendMessageResponse extends AbstractResponse {

    private int total;
    private int success;
    private int failed;
}