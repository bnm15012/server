package com.dancestudio.erp.modules.member.memberActiveStatus;

import java.util.Date;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.modules.member.Member;

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

    /** ====================== UTIL HELPERS ====================== **/

    private static Date minDate(Date d1, Date d2) {
        if (d1 == null)
            return d2;
        if (d2 == null)
            return d1;
        return d2.before(d1) ? d2 : d1;
    }

    private static Date maxDate(Date d1, Date d2) {
        if (d2 == null)
            return d2;
        if (d1 == null)
            return d1;
        return d2.after(d1) ? d2 : d1;
    }

    /**
     * Detects whether deleting a date range changes the boundary window (min/max)
     * IMPORTANT: Handles NULL end dates correctly.
     */
    private static boolean deletionBreaksWindow(Date currentEarliest, Date currentLatest,
            Date deletedStart, Date deletedEnd) {

        boolean breaksStart = deletedStart != null &&
                currentEarliest != null &&
                deletedStart.equals(currentEarliest);

        boolean breaksEnd =
                // Both null → deleting the only open-ended range
                (deletedEnd == null && currentLatest == null) ||
                // Exact match
                        (deletedEnd != null && deletedEnd.equals(currentLatest));

        return breaksStart || breaksEnd;
    }

    /**
     * ==============================================================
     * ADD NEW ASSIGNMENT
     * ==============================================================
     */
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
            // New member → Create fresh entry
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

            if (deletionBreaksWindow(entry.getEarliestStartDate(), entry.getLatestEndDate(),
                    oldStart, oldEnd)) {

                MemberActiveStatusEntry rebuilt = manager.rebuildWindowForMember(memberId);

                if (rebuilt == null) {
                    return;
                }

                // Now merge new range
                rebuilt.setEarliestStartDate(minDate(rebuilt.getEarliestStartDate(), newStart));
                rebuilt.setLatestEndDate(maxDate(rebuilt.getLatestEndDate(), newEnd));

                manager.update(memberId, rebuilt);
                return;
            }

            // Otherwise just merge new dates
            entry.setEarliestStartDate(minDate(entry.getEarliestStartDate(), newStart));
            entry.setLatestEndDate(maxDate(entry.getLatestEndDate(), newEnd));

            manager.update(memberId, entry);

        } catch (EntityNotFoundException ex) {
            // No entry exists → create new
            manager.add(new MemberActiveStatusEntry(memberId, newStart, newEnd));
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

                MemberActiveStatusEntry rebuilt = manager.rebuildWindowForMember(memberId);
                manager.update(memberId, rebuilt);
            }

        } catch (EntityNotFoundException ex) {
            // No entry exists → but user deleted something?
            // Create neutral entry so system is consistent
            manager.add(new MemberActiveStatusEntry(memberId, start, end));
        }
    }
}
