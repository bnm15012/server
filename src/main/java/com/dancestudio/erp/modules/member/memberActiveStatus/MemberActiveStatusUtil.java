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
        if (d1 == null)
            return d2;
        if (d2 == null)
            return d1;
        return d2.after(d1) ? d2 : d1;
    }

    private static boolean deletionBreaksWindow(Date currentEarliest, Date currentLatest,
            Date deletedStart, Date deletedEnd) {
        boolean breaksStart = deletedStart != null && deletedStart.equals(currentEarliest);
        boolean breaksEnd = deletedEnd != null && deletedEnd.equals(currentLatest);
        return breaksStart || breaksEnd;
    }

    /**
     * ==============================================================
     * ADD NEW ASSIGNMENT
     * If row doesn't exist → create new (O(1))
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
            // Create new row
            manager.add(new MemberActiveStatusEntry(memberId, start, end));
        }
    }

    /**
     * ==============================================================
     * UPDATE ASSIGNMENT
     * - On update, remove old range and add new range
     * ==============================================================
     */
    public static void updateNewAssignment(Member member, Date oldStart, Date oldEnd,
            Date newStart, Date newEnd)
            throws Exception {
        MemberActiveStatusManager manager = getManager();
        try {
            Long memberId = member.getId();

            MemberActiveStatusEntry entry = manager.getById(memberId);

            // If old window affects the boundary → full rescan required
            if (deletionBreaksWindow(entry.getEarliestStartDate(), entry.getLatestEndDate(), oldStart, oldEnd)) {

                // 🟡 REBUILD WINDOW ONLY FOR THIS USER
                MemberActiveStatusEntry rebuilt = manager.rebuildWindowForMember(memberId);

                // Apply updated range
                rebuilt.setEarliestStartDate(minDate(rebuilt.getEarliestStartDate(), newStart));
                rebuilt.setLatestEndDate(maxDate(rebuilt.getLatestEndDate(), newEnd));

                manager.update(memberId, rebuilt);
                return;
            }

            // If no boundary effect → just merge new date
            entry.setEarliestStartDate(minDate(entry.getEarliestStartDate(), newStart));
            entry.setLatestEndDate(maxDate(entry.getLatestEndDate(), newEnd));

            manager.update(memberId, entry);
        } catch (EntityNotFoundException ex) {
            // Create new row
            manager.add(new MemberActiveStatusEntry(member.getId(), newStart, newEnd));
        }
    }

    /**
     * ==============================================================
     * DELETE ASSIGNMENT
     * ==============================================================
     * 
     * @throws Exception
     * @throws EntityNotFoundException
     */
    public static void deleteNewAssignment(Member member, Date start, Date end)
            throws EntityNotFoundException, Exception {

        MemberActiveStatusManager manager = getManager();
        try {
            Long memberId = member.getId();

            MemberActiveStatusEntry entry = manager.getById(memberId);

            // If deletion breaks min/max window → full rescan required
            if (deletionBreaksWindow(entry.getEarliestStartDate(), entry.getLatestEndDate(), start, end)) {
                MemberActiveStatusEntry rebuilt = manager.rebuildWindowForMember(memberId);
                manager.update(memberId, rebuilt);
            }
        } catch (EntityNotFoundException ex) {
            // Create new row
            manager.add(new MemberActiveStatusEntry(member.getId(), start, end));
        }
        // If not affecting boundary → do nothing (window stays same)
    }
}
