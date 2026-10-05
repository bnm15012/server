package com.dancestudio.erp.modules.plan;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.enums.SubscriptionType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.util.GeoLocationUtil;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class PlanManager extends BaseManager<Plan, Long, PlanEntry> {
    private final PlanRepository planRepository;
    private final StudioPlanRepository studioPlanRepository;
    private final GeoLocationUtil geoLocationUtil;

    public PlanManager(PlanRepository planRepository, StudioPlanRepository studioPlanRepository,
            GeoLocationUtil geoLocationUtil) {
        super(planRepository, "plan");
        this.planRepository = planRepository;
        this.studioPlanRepository = studioPlanRepository;
        this.geoLocationUtil = geoLocationUtil;
    }

    public List<PlanEntry> getAllPlans(HttpServletRequest request, Boolean AMC) throws EntityNotFoundException {
        String ip = geoLocationUtil.extractClientIp(request);
        String countryCode = geoLocationUtil.getCountryCode(ip);

        List<Plan> plans = planRepository.findPlansByCountryCodeAndAmcFlag(countryCode, AMC);

        List<PlanEntry> planEntries = new ArrayList<>();
        for (Plan plan : plans) {
            if (!plan.getPlanType().equals(SubscriptionType.TRIAL.name())) {
                PlanEntry planEntry = PlanConvertor.convertToEntry(plan);
                planEntries.add(planEntry);
            }
        }
        return planEntries;
    }

    public PlanEntry getPlansByMembershipTypeAndCountryCode(String membershipType, String countryCode)
            throws EntityNotFoundException {
        Plan plan = planRepository.findByPlanTypeAndCountryCode(membershipType, countryCode);
        if (Objects.isNull(plan)) {
            throw new EntityNotFoundException("No plans found for the given membership type and country code");
        }
        return PlanConvertor.convertToEntry(plan);
    }

    public List<PlanEntry> getPlansForStudio(Long studioId, HttpServletRequest request, Boolean AMC)
            throws EntityNotFoundException {
        String ip = geoLocationUtil.extractClientIp(request);
        String countryCode = geoLocationUtil.getCountryCode(ip);

        List<Plan> plans = planRepository.findPlansByCountryCodeAndAmcFlag(countryCode, AMC);
        List<StudioPlan> studioPlans = studioId != null ? studioPlanRepository.findByStudioId(studioId) : Collections.emptyList();

        List<PlanEntry> planEntries = new ArrayList<>();
        for (Plan plan : plans) {
            if (plan.getPlanType().equals(SubscriptionType.TRIAL.name())) {
                continue;
            }
            PlanEntry planEntry = PlanConvertor.convertToEntry(plan);

            Optional<StudioPlan> studioPlan = studioPlans.stream()
                    .filter(sp -> sp.getPlan().getId().equals(plan.getId()))
                    .findFirst();
            if (studioPlan.isPresent()) {
                planEntry.setAmount(studioPlan.get().getCustomAmount());
            }
            planEntries.add(planEntry);
        }
        return planEntries;
    }

    public PlanEntry getStudioPlanByMembershipType(Long studioId, String membershipType,
            String countryCode) throws EntityNotFoundException {
        Plan plan = planRepository.findByPlanTypeAndCountryCode(membershipType, countryCode);
        if (Objects.isNull(plan)) {
            throw new EntityNotFoundException(
                    "No plans found for the given membership type and country code");
        }
        PlanEntry planEntry = PlanConvertor.convertToEntry(plan);

        Optional<StudioPlan> studioPlan =
                studioPlanRepository.findByStudioIdAndPlanType(studioId, membershipType);
        if (studioPlan.isPresent()) {
            planEntry.setAmount(studioPlan.get().getCustomAmount());
        }
        return planEntry;
    }

    @Override
    protected Plan toEntity(PlanEntry entry, Plan existing) throws EntityNotFoundException, BeansException, Exception {
        return PlanConvertor.convertToEntity(entry, existing);
    }

    @Override
    protected PlanEntry toEntry(Plan entity, String[] fields) throws EntityNotFoundException {
        return PlanConvertor.convertToEntry(entity);
    }

}
