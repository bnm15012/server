package com.dancestudio.erp.modules.membershipPackage;

import com.dancestudio.erp.base.BaseController;
import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.base.BaseService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membershipPackages")
public class MembershipPackagesController extends BaseController<MembershipPackagesEntry, Long> {

    @Autowired
    private MembershipPackagesService membershipPackagesService;

    @Override
    protected BaseService<MembershipPackagesEntry, Long> getService() {
        return membershipPackagesService;
    }

    @GetMapping("/getAll/{studioId}")
    public ResponseEntity<BaseResponse<MembershipPackagesEntry>> getAll(@PathVariable Long studioId, 
        @RequestParam(defaultValue = "1") Integer page, 
        @RequestParam(defaultValue = "10") Integer size) {
        return membershipPackagesService.getAllByStudioId(studioId, page, size);
    }
}
