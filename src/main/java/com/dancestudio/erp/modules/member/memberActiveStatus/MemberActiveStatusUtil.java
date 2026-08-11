package com.dancestudio.erp.modules.member.memberActiveStatus;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.enums.MembershipStatus;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.util.DateUtil;

import jakarta.annotation.PostConstruct;

@Component
public class MemberActiveStatusUtil {

    private static ApplicationContext applicationContext;

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        applicationContext = context;
    }

    private static MemberActiveStatusManager getManager() {
        return applicationContext.getBean(MemberActiveStatusManager.class);
    }


    public static void addNewAssignment(Member member, Date start, Date end)
            throws BeansException, EntityNotFoundException, Exception {

        MemberActiveStatusManager manager = getManager();
        Long memberId = member.getId();

        MemberActiveStatusEntry entry = manager.findByMemberId(memberId);
        if (entry != null) {
            List<ActivePeriod> periods = new ArrayList<>(entry.getActivePeriods());
            periods.add(new ActivePeriod(start, end));

            periods = mergePeriods(periods);
            periods = removeExpiredPeriods(periods);

            entry.setActivePeriods(periods);
            manager.update(memberId, entry);
        } else {
            List<ActivePeriod> periods = new ArrayList<>();
            periods.add(new ActivePeriod(start, end));
            periods = removeExpiredPeriods(periods);

            MemberActiveStatusEntry newEntry = new MemberActiveStatusEntry(memberId, periods);
            manager.add(newEntry);
        }
    }
    public static void updateNewAssignment(Member member, Date oldStart, Date oldEnd,
            Date newStart, Date newEnd)
            throws Exception {

        MemberActiveStatusManager manager = getManager();
        Long memberId = member.getId();

        manager.rebuildPeriodsForMember(memberId, member.getMemberType());
    }

    public static void deleteNewAssignment(Member member, Date start, Date end)
            throws EntityNotFoundException, Exception {

        MemberActiveStatusManager manager = getManager();
        Long memberId = member.getId();

        manager.rebuildPeriodsForMember(memberId, member.getMemberType());
    }

    public static Boolean isMembershipActive(Date start, Date end) {
        Date today = DateUtil.getCurrentDateUTC();
        if (start == null)
            return false;

        boolean started = !today.before(start);
        if (end == null)
            return started;

        boolean notEnded = !today.after(end);
        return started && notEnded;
    }

    public static MembershipStatus getMembershipStatus(Date start, Date end) {
        if (isMembershipActive(start, end)) {
            return MembershipStatus.ACTIVE;
        } else {
            return MembershipStatus.INACTIVE;
        }
    }

    static List<ActivePeriod> mergePeriods(List<ActivePeriod> periods) {
        if (periods == null || periods.isEmpty()) {
            return new ArrayList<>();
        }

        List<ActivePeriod> sorted = new ArrayList<>(periods);
        sorted.sort(Comparator.comparing(ActivePeriod::getStartDate, Comparator.nullsFirst(Comparator.naturalOrder())));

        List<ActivePeriod> merged = new ArrayList<>();
        ActivePeriod current = new ActivePeriod(sorted.get(0).getStartDate(), sorted.get(0).getEndDate());

        for (int i = 1; i < sorted.size(); i++) {
            ActivePeriod next = sorted.get(i);

            if (shouldMerge(current, next)) {
                // Expand the current period to cover both
                current.setEndDate(maxDate(current.getEndDate(), next.getEndDate()));
            } else {
                merged.add(current);
                current = new ActivePeriod(next.getStartDate(), next.getEndDate());
            }
        }
        merged.add(current);

        return merged;
    }


    static List<ActivePeriod> removeExpiredPeriods(List<ActivePeriod> periods) {
        if (periods == null || periods.isEmpty()) {
            return new ArrayList<>();
        }

        Date today = DateUtil.getCurrentDateUTC();
        List<ActivePeriod> kept = new ArrayList<>();
        for (ActivePeriod p : periods) {
            if (p.getEndDate() == null || !p.getEndDate().before(today)) {
                kept.add(p);
            }
        }
        return kept;
    }

    private static boolean shouldMerge(ActivePeriod current, ActivePeriod next) {
        if (current.getEndDate() == null) {
            return true;
        }

        Date currentEnd = current.getEndDate();
        Date nextStart = next.getStartDate();

        if (!nextStart.after(currentEnd)) {
            return true;
        }
        Date dayAfterCurrentEnd = DateUtil.addDays(currentEnd, 1);
        return !nextStart.after(dayAfterCurrentEnd);
    }


    private static Date maxDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return null; 
        }
        return d2.after(d1) ? d2 : d1;
    }
}
