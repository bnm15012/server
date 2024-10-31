package com.dancestudio.erp.service.impl;

import com.dancestudio.erp.entry.MembershipFeeEntry;
import com.dancestudio.erp.manager.MembershipFeeManager;
import com.dancestudio.erp.response.MembershipFeeResponse;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.service.MembershipFeeService;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Setter(onMethod = @__({@Autowired}))
@Component
public class MembershipFeeServiceImpl implements MembershipFeeService {

    private MembershipFeeManager membershipFeeManager;

    @Override
    public MembershipFeeResponse addMembershipFee(MembershipFeeEntry membershipFeeEntry) {
        MembershipFeeResponse response = new MembershipFeeResponse();
        try {
            MembershipFeeEntry entry = membershipFeeManager.addMembershipFee(membershipFeeEntry);
            response.setStatus(new StatusResponse(1, "Membership fee entry added Successfully", StatusResponse.Type.SUCCESS));
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
        }
        return response;
    }

    @Override
    public MembershipFeeResponse updateMembershipFee(Long id, Double newFeeAmount) {
        MembershipFeeResponse response = new MembershipFeeResponse();
        try {
            MembershipFeeEntry entry = membershipFeeManager.updateMembershipFee(id, newFeeAmount);
            response.setStatus(new StatusResponse(1, "Membership fee updated Successfully", StatusResponse.Type.SUCCESS));
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
        }
        return response;
    }

    @Override
    public MembershipFeeResponse getMembershipFeesByStudio(Long studioId) {
        MembershipFeeResponse response = new MembershipFeeResponse();
        try {
            List<MembershipFeeEntry> entries = membershipFeeManager.getMembershipFeesByStudio(studioId);
            response.setStatus(new StatusResponse(1, StatusResponse.Type.SUCCESS, Objects.isNull(entries) ? 0 : entries.size()));
        } catch (Exception ex) {
            response.setStatus(new StatusResponse(1, ex.getMessage(), StatusResponse.Type.ERROR));
        }
        return response;
    }
}
