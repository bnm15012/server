package com.dancestudio.erp.modules.superadmin;

import com.dancestudio.erp.authentication.JwtUtil;
import com.dancestudio.erp.base.BaseResponse;
import com.dancestudio.erp.entity.Studio;
import com.dancestudio.erp.entity.User;
import com.dancestudio.erp.entry.StudioEntry;
import com.dancestudio.erp.entry.SubscriptionEntry;
import com.dancestudio.erp.entry.UserEntry;
import com.dancestudio.erp.enums.SubscriptionStatus;
import com.dancestudio.erp.enums.SubscriptionType;
import com.dancestudio.erp.enums.UserType;
import com.dancestudio.erp.manager.SubscriptionManager;
import com.dancestudio.erp.modules.plan.Plan;
import com.dancestudio.erp.modules.plan.PlanRepository;
import com.dancestudio.erp.modules.plan.StudioPlan;
import com.dancestudio.erp.modules.plan.StudioPlanEntry;
import com.dancestudio.erp.modules.plan.PlanConvertor;
import com.dancestudio.erp.modules.plan.PlanEntry;
import com.dancestudio.erp.modules.plan.StudioPlanRepository;
import com.dancestudio.erp.modules.studio.StudioConvertor;
import com.dancestudio.erp.entity.Subscription;
import com.dancestudio.erp.repository.StudioRepository;
import com.dancestudio.erp.repository.SubscriptionRepository;
import com.dancestudio.erp.repository.UserRepository;
import com.dancestudio.erp.response.StatusResponse;
import com.dancestudio.erp.response.StudioResponse;
import com.dancestudio.erp.response.UserResponse;
import com.dancestudio.erp.util.ConvertToEntryUtil;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Setter(onMethod = @__({@Autowired}))
@Component
public class SuperAdminService {

    private StudioRepository studioRepository;
    private UserRepository userRepository;
    private SubscriptionManager subscriptionManager;
    private SubscriptionRepository subscriptionRepository;
    private JwtUtil jwtUtil;
    private StudioPlanRepository studioPlanRepository;
    private PlanRepository planRepository;

    public ResponseEntity<Map<String, Object>> getDashboardStats() {
        try {
            List<Studio> studios = studioRepository.findAll();
            int totalStudios = studios.size();
            int activeCount = 0;
            int expiredCount = 0;
            int trialCount = 0;

            for (Studio studio : studios) {
                SubscriptionEntry sub = subscriptionManager.getSubscriptionPlanByStudioId(studio.getId());
                if (sub != null && sub.getStatus() != null) {
                    SubscriptionStatus status = sub.getStatus();
                    SubscriptionType plan = sub.getSubscriptionPlan();
                    if (SubscriptionStatus.ACTIVE == status) {
                        if (SubscriptionType.TRIAL == plan) {
                            trialCount++;
                        } else {
                            activeCount++;
                        }
                    } else {
                        expiredCount++;
                    }
                } else {
                    expiredCount++;
                }
            }

            List<Subscription> allSubs = subscriptionRepository.findAll();
            BigDecimal totalRevenue = BigDecimal.ZERO;
            BigDecimal currentMonthRevenue = BigDecimal.ZERO;
            BigDecimal lastMonthRevenue = BigDecimal.ZERO;

            Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            int currentMonth = now.get(Calendar.MONTH);
            int currentYear = now.get(Calendar.YEAR);

            Calendar lastMonthCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            lastMonthCal.add(Calendar.MONTH, -1);
            int lastMonth = lastMonthCal.get(Calendar.MONTH);
            int lastMonthYear = lastMonthCal.get(Calendar.YEAR);

            Map<String, BigDecimal> monthlyRevenue = new LinkedHashMap<>();
            Map<String, Integer> revenueByPlan = new LinkedHashMap<>();
            SimpleDateFormat monthFmt = new SimpleDateFormat("MMM yyyy");
            monthFmt.setTimeZone(TimeZone.getTimeZone("UTC"));

            for (Subscription sub : allSubs) {
                if (!"ACTIVE".equalsIgnoreCase(sub.getStatus())) continue;
                if ("TRIAL".equalsIgnoreCase(sub.getSubscriptionPlan())) continue;

                BigDecimal price = sub.getPrice() != null ? sub.getPrice() : BigDecimal.ZERO;
                totalRevenue = totalRevenue.add(price);

                Calendar subCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                subCal.setTime(sub.getStartDate());
                int subMonth = subCal.get(Calendar.MONTH);
                int subYear = subCal.get(Calendar.YEAR);

                if (subMonth == currentMonth && subYear == currentYear) {
                    currentMonthRevenue = currentMonthRevenue.add(price);
                }
                if (subMonth == lastMonth && subYear == lastMonthYear) {
                    lastMonthRevenue = lastMonthRevenue.add(price);
                }

                String monthKey = monthFmt.format(sub.getStartDate());
                monthlyRevenue.merge(monthKey, price, BigDecimal::add);

                String planType = sub.getSubscriptionPlan() != null ? sub.getSubscriptionPlan() : "OTHER";
                revenueByPlan.merge(planType, 1, Integer::sum);
            }

            List<Map<String, Object>> monthlyTrend = new ArrayList<>();
            Calendar trendCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            trendCal.add(Calendar.MONTH, -5);
            for (int i = 0; i < 6; i++) {
                String key = monthFmt.format(trendCal.getTime());
                Map<String, Object> entry = new LinkedHashMap<>();
                entry.put("month", key);
                entry.put("revenue", monthlyRevenue.getOrDefault(key, BigDecimal.ZERO));
                monthlyTrend.add(entry);
                trendCal.add(Calendar.MONTH, 1);
            }

            Map<String, Object> stats = new LinkedHashMap<>();
            stats.put("totalStudios", totalStudios);
            stats.put("activeSubscriptions", activeCount);
            stats.put("expiredSubscriptions", expiredCount);
            stats.put("trialStudios", trialCount);
            stats.put("totalRevenue", totalRevenue);
            stats.put("currentMonthRevenue", currentMonthRevenue);
            stats.put("lastMonthRevenue", lastMonthRevenue);
            stats.put("monthlyTrend", monthlyTrend);
            stats.put("revenueByPlan", revenueByPlan);
            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }

    public ResponseEntity<StudioResponse> getAllStudiosWithSubscription() {
        StudioResponse response = new StudioResponse();
        try {
            List<Studio> studios = studioRepository.findAll();
            List<StudioEntry> entries = new ArrayList<>();
            for (Studio studio : studios) {
                StudioEntry entry = StudioConvertor.convertToEntry(studio);
                SubscriptionEntry sub = subscriptionManager.getSubscriptionPlanByStudioId(studio.getId());
                entry.setSubscriptionEntry(sub);
                entries.add(entry);
            }
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "All studios fetched",
                    StatusResponse.Type.SUCCESS, entries.size()));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<UserResponse> loginAsStudio(Long studioId) {
        UserResponse response = new UserResponse();
        try {
            Studio studio = studioRepository.findById(studioId)
                    .orElseThrow(() -> new RuntimeException("Studio not found"));

            List<User> users = userRepository.findByStudioId(studioId);
            User adminUser = users.stream()
                    .filter(u -> UserType.ADMIN.name().equals(u.getRole()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("No admin user found for studio"));

            UserEntry entry = ConvertToEntryUtil.convertToEntry(adminUser);

            Map<String, Object> claims = new HashMap<>();
            if (entry.getSubscriptionEntry() != null) {
                claims.put("membershipEndDate", entry.getSubscriptionEntry().getEndDate());
            }
            if (adminUser.getBranch() != null) {
                claims.put("branchId", adminUser.getBranch().getId());
                claims.put("studioId", adminUser.getBranch().getStudio().getId());
            } else {
                claims.put("studioId", studio.getId());
            }

            String token = jwtUtil.generateAuthToken(adminUser.getEmail(), claims);
            entry.setToken(token);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Logged in as studio admin",
                    StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<BaseResponse<StudioPlanEntry>> upsertStudioPlan(StudioPlanEntry entry) {
        BaseResponse<StudioPlanEntry> response = new BaseResponse<>();
        try {
            Studio studio = studioRepository.findById(entry.getStudioId())
                    .orElseThrow(() -> new RuntimeException("Studio not found"));
            Plan plan = planRepository.findById(entry.getPlanId())
                    .orElseThrow(() -> new RuntimeException("Plan not found"));

            Optional<StudioPlan> existing = studioPlanRepository
                    .findByStudioIdAndPlanType(studio.getId(), plan.getPlanType());

            StudioPlan studioPlan;
            if (existing.isPresent()) {
                studioPlan = existing.get();
                studioPlan.setCustomAmount(entry.getCustomAmount());
            } else {
                studioPlan = new StudioPlan();
                studioPlan.setStudio(studio);
                studioPlan.setPlan(plan);
                studioPlan.setCustomAmount(entry.getCustomAmount());
            }
            studioPlan = studioPlanRepository.save(studioPlan);

            StudioPlanEntry resultEntry = new StudioPlanEntry();
            resultEntry.setId(studioPlan.getId());
            resultEntry.setStudioId(studio.getId());
            resultEntry.setPlanId(plan.getId());
            resultEntry.setCustomAmount(studioPlan.getCustomAmount());

            response.setData(Collections.singletonList(resultEntry));
            response.setStatus(new StatusResponse(1, "Studio plan saved",
                    StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<BaseResponse<StudioPlanEntry>> getStudioPlans(Long studioId) {
        BaseResponse<StudioPlanEntry> response = new BaseResponse<>();
        try {
            List<StudioPlan> studioPlans = studioPlanRepository.findByStudioId(studioId);
            List<StudioPlanEntry> entries = new ArrayList<>();
            for (StudioPlan sp : studioPlans) {
                StudioPlanEntry e = new StudioPlanEntry();
                e.setId(sp.getId());
                e.setStudioId(studioId);
                e.setPlanId(sp.getPlan().getId());
                e.setCustomAmount(sp.getCustomAmount());
                entries.add(e);
            }
            response.setData(entries);
            response.setStatus(new StatusResponse(1, "Studio plans fetched",
                    StatusResponse.Type.SUCCESS, entries.size()));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    public ResponseEntity<BaseResponse<StudioPlanEntry>> deleteStudioPlan(Long id) {
        BaseResponse<StudioPlanEntry> response = new BaseResponse<>();
        try {
            studioPlanRepository.deleteById(id);
            response.setStatus(new StatusResponse(1, "Studio plan deleted",
                    StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    public ResponseEntity<BaseResponse<PlanEntry>> updatePlan(Long id, PlanEntry entry) {
        BaseResponse<PlanEntry> response = new BaseResponse<>();
        try {
            Plan existing = planRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Plan not found"));
            Plan updated = PlanConvertor.convertToEntity(entry, existing);
            updated = planRepository.save(updated);
            PlanEntry resultEntry = PlanConvertor.convertToEntry(updated);
            response.setData(Collections.singletonList(resultEntry));
            response.setStatus(new StatusResponse(1, "Plan updated",
                    StatusResponse.Type.SUCCESS));
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @jakarta.transaction.Transactional
    public ResponseEntity<Map<String, Object>> getRevenueDetails(Integer month, Integer year) {
        try {
            Calendar now = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
            int filterMonth = month != null ? month - 1 : now.get(Calendar.MONTH);
            int filterYear = year != null ? year : now.get(Calendar.YEAR);

            List<Subscription> allSubs = subscriptionRepository.findAll();
            List<Map<String, Object>> payments = new ArrayList<>();
            BigDecimal totalAmount = BigDecimal.ZERO;

            for (Subscription sub : allSubs) {
                if (!"ACTIVE".equalsIgnoreCase(sub.getStatus())) continue;
                if ("TRIAL".equalsIgnoreCase(sub.getSubscriptionPlan())) continue;

                Calendar subCal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                subCal.setTime(sub.getStartDate());

                if (subCal.get(Calendar.MONTH) == filterMonth && subCal.get(Calendar.YEAR) == filterYear) {
                    Map<String, Object> payment = new LinkedHashMap<>();
                    payment.put("subscriptionId", sub.getId());
                    payment.put("studioName", sub.getStudio().getName());
                    payment.put("studioId", sub.getStudio().getId());
                    payment.put("plan", sub.getSubscriptionPlan());
                    payment.put("amount", sub.getPrice());
                    payment.put("startDate", sub.getStartDate());
                    payment.put("endDate", sub.getEndDate());
                    payment.put("status", sub.getStatus());
                    payment.put("orderId", sub.getOrderId());
                    payment.put("paymentId", sub.getPaymentId());
                    payments.add(payment);
                    totalAmount = totalAmount.add(sub.getPrice() != null ? sub.getPrice() : BigDecimal.ZERO);
                }
            }

            Map<String, Object> result = new LinkedHashMap<>();
            result.put("month", filterMonth + 1);
            result.put("year", filterYear);
            result.put("totalAmount", totalAmount);
            result.put("totalPayments", payments.size());
            result.put("payments", payments);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            Map<String, Object> error = new HashMap<>();
            error.put("error", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
        }
    }
}
