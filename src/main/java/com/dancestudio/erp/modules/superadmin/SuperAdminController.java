package com.dancestudio.erp.modules.superadmin;

import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.modules.plan.PlanEntry;
import com.dancestudio.erp.modules.plan.StudioPlanEntry;
import com.dancestudio.erp.response.StudioResponse;
import com.dancestudio.erp.response.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/super-admin")
public class SuperAdminController {

  @Autowired private SuperAdminService superAdminService;

  @GetMapping("/dashboard")
  public ResponseEntity<Map<String, Object>> getDashboardStats() {
    return superAdminService.getDashboardStats();
  }

  @GetMapping("/studios")
  public ResponseEntity<StudioResponse> getAllStudiosWithDetails() {
    return superAdminService.getAllStudiosWithSubscription();
  }

  @PostMapping("/login-as/{studioId}")
  public ResponseEntity<UserResponse> loginAsStudio(@PathVariable Long studioId) {
    return superAdminService.loginAsStudio(studioId);
  }

  @PostMapping("/studio-plans")
  public ResponseEntity<?> upsertStudioPlan(@RequestBody StudioPlanEntry entry) {
    return superAdminService.upsertStudioPlan(entry);
  }

  @GetMapping("/studio-plans/{studioId}")
  public ResponseEntity<?> getStudioPlans(@PathVariable Long studioId) {
    return superAdminService.getStudioPlans(studioId);
  }

  @DeleteMapping("/studio-plans/{id}")
  public ResponseEntity<?> deleteStudioPlan(@PathVariable Long id) {
    return superAdminService.deleteStudioPlan(id);
  }

  @PutMapping("/plans/update/{id}")
  public ResponseEntity<BaseResponse<PlanEntry>> updatePlan(@PathVariable Long id, @RequestBody PlanEntry entry) {
    return superAdminService.updatePlan(id, entry);
  }

  @GetMapping("/revenue")
  public ResponseEntity<Map<String, Object>> getRevenue(
          @RequestParam(required = false) Integer month,
          @RequestParam(required = false) Integer year) {
    return superAdminService.getRevenueDetails(month, year);
  }
}
