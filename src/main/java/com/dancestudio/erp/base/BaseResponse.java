package com.dancestudio.erp.base;

import com.dancestudio.erp.response.StatusResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseResponse<T> {
    private List<T> data;
    private StatusResponse status;
}
