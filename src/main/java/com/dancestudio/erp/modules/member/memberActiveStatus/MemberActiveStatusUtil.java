package com.dancestudio.erp.modules.member.memberActiveStatus;

import java.util.Date;
import java.util.Objects;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

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

    private static Date minDate(Date d1, Date d2) {
        return d2.before(d1) ? d2 : d1;
    }

    private static Date maxDate(Date d1, Date d2) {
        if (d2 == null || d1 == null)
            return null;
        return d2.after(d1) ? d2 : d1;
    }

    private static Boolean deletionBreaksWindow(Date currentEarliest, Date currentLatest,
            Date deletedStart, Date deletedEnd) {
        if (currentEarliest.equals(deletedStart) && Objects.equals(currentLatest, deletedEnd)) {
            return true;
        } else if (currentEarliest.equals(deletedStart)) {
            return true;
        } else if (Objects.equals(currentLatest, deletedEnd)) {
            return true;
        } else {
            return false;
        }
    }

    public static void addNewAssignment(Member member, Date start, Date end)
            throws BeansException, EntityNotFoundException, Exception {

        MemberActiveStatusManager manager = getManager();
        Long memberId = member.getId();

        try {
            MemberActiveStatusEntry entry = manager.getById(memberId);

            entry.setEarliestStartDate(minDate(entry.getEarliestStartDate(), start));
            entry.setLatestEndDate(maxDate(entry.getLatestEndDate(), end));

            manager.update(memberId, entry);

        } catch (EntityNotFoundException ex) {
            manager.add(new MemberActiveStatusEntry(memberId, start, end));
        }
    }

    /**
     * ==============================================================
     * UPDATE ASSIGNMENT
     * ==============================================================
     * On update:
     * - Remove old window
     * - Apply new window
     */
    public static void updateNewAssignment(Member member, Date oldStart, Date oldEnd,
            Date newStart, Date newEnd)
            throws Exception {

        MemberActiveStatusManager manager = getManager();
        Long memberId = member.getId();

        try {
            MemberActiveStatusEntry entry = manager.getById(memberId);
            entry.setEarliestStartDate(minDate(entry.getEarliestStartDate(), newStart));
            entry.setLatestEndDate(maxDate(entry.getLatestEndDate(), newEnd));
            manager.update(memberId, entry);
            if (deletionBreaksWindow(entry.getEarliestStartDate(), entry.getLatestEndDate(),
                    oldStart, oldEnd)) {
                manager.rebuildWindowForMember(memberId, member.getMemberType());
            }
        } catch (EntityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * ==============================================================
     * DELETE ASSIGNMENT
     * ==============================================================
     */
    public static void deleteNewAssignment(Member member, Date start, Date end)
            throws EntityNotFoundException, Exception {

        MemberActiveStatusManager manager = getManager();
        Long memberId = member.getId();

        try {
            MemberActiveStatusEntry entry = manager.getById(memberId);

            // If deletion affects min/max window → rebuild
            if (deletionBreaksWindow(entry.getEarliestStartDate(), entry.getLatestEndDate(),
                    start, end)) {
                manager.rebuildWindowForMember(memberId, member.getMemberType());
            }

        } catch (EntityNotFoundException ex) {
            // No entry exists → but user deleted something?
            // Create neutral entry so system is consistent
            manager.add(new MemberActiveStatusEntry(memberId, start, end));
        }
    }

    public static Boolean isMembershipActive(Date start, Date end) {
        Date today = DateUtil.getTodayDate();
        if (start == null)
            return false;

        boolean started = !today.before(start);

        if (end == null)
            return started;

        boolean notEnded = !today.after(end);
        return started && notEnded;
    }
}
