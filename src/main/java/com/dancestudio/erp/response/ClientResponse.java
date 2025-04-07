package com.dancestudio.erp.response;


import com.dancestudio.erp.entry.ClientEntry;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientResponse extends AbstractResponse {
    private List<ClientEntry> data;
}