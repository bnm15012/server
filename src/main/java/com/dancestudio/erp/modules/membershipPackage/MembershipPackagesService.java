package com.dancestudio.erp.modules.membershipPackage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;
import com.dancestudio.erp.response.StatusResponse;

import lombok.Setter;

@Setter(onMethod = @__({ @Autowired }))
@Component
public class MembershipPackagesService
        extends BaseService<MembershipPackagesEntry, Long> {


    private MembershipPackagesManager manager;
    public ResponseEntity< BaseResponse<MembershipPackagesEntry>> getAllByStudioId(Long stuidId, Integer page, Integer size) {
  BaseResponse<MembershipPackagesEntry> response = new BaseResponse<>();

        try {
            Page<MembershipPackagesEntry> entries = manager.getAllPackagesByStudioId(stuidId, --page, size);

            response.setData(entries.getContent());

            response.setStatus(new StatusResponse(1, "Expenses retrieved successfully", StatusResponse.Type.SUCCESS,
                    entries.getTotalElements()));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }

    }

    @Override
    protected MembershipPackagesEntry doAdd(MembershipPackagesEntry entry) throws Exception {
        return manager.add(entry);
    }

    @Override
    protected MembershipPackagesEntry doUpdate(Long id, MembershipPackagesEntry entry) throws Exception {
        return manager.update(id, entry);
    }

    @Override
    protected void doDelete(Long id) throws Exception {
        manager.delete(id);
    }

    @Override
    protected MembershipPackagesEntry doGet(Long id) throws Exception {
        return manager.getById(id);
    }

}
