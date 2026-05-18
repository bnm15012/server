package com.dancestudio.erp.modules.plan;

import com.dancestudio.erp.base.BaseManager;
import com.dancestudio.erp.enums.SubscriptionType;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.util.GeoLocationUtil;
import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.BeansException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class PlanManager extends BaseManager<Plan, Long, PlanEntry> {
    private final PlanRepository planRepository;
    private final GeoLocationUtil geoLocationUtil;

    public PlanManager(PlanRepository planRepository, GeoLocationUtil geoLocationUtil) {
        super(planRepository, "plan");
        this.planRepository = planRepository;
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

    @Override
    protected Plan toEntity(PlanEntry entry, Plan existing) throws EntityNotFoundException, BeansException, Exception {
        return PlanConvertor.convertToEntity(entry, existing);
    }

    @Override
    protected PlanEntry toEntry(Plan entity, String[] fields) throws EntityNotFoundException {
        return PlanConvertor.convertToEntry(entity);
    }

}
