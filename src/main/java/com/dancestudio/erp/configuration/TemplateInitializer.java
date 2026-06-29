package com.dancestudio.erp.configuration;

import com.dancestudio.erp.modules.member.Member;
import com.dancestudio.erp.modules.member.instructor.instructorActivityAssignment.InstructorActivityAssignment;
import com.dancestudio.erp.modules.member.instructor.instructorActivityAssignment.InstructorActivityAssignmentRepository;
import com.dancestudio.erp.modules.member.memberActiveStatus.ActivePeriod;
import com.dancestudio.erp.modules.member.memberActiveStatus.MemberActiveStatus;
import com.dancestudio.erp.modules.member.memberActiveStatus.MemberActiveStatusRepository;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignment;
import com.dancestudio.erp.modules.member.student.StudentActivityAssignment.StudentActivityAssignmentRepository;
import com.dancestudio.erp.modules.template.template.Template;
import com.dancestudio.erp.modules.template.template.TemplateRepository;
import com.dancestudio.erp.util.DateUtil;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class TemplateInitializer implements CommandLineRunner {

    private final TemplateRepository templateRepository;
    private final StudentActivityAssignmentRepository studentActivityAssignmentRepository;
    private final InstructorActivityAssignmentRepository instructorActivityAssignmentRepository;
    private final MemberActiveStatusRepository memberActiveStatusRepository;
    private final ObjectMapper objectMapper;

    public TemplateInitializer(TemplateRepository templateRepository, ObjectMapper objectMapper,
            MemberActiveStatusRepository memberActiveStatusRepository,
            StudentActivityAssignmentRepository studentActivityAssignmentRepository, InstructorActivityAssignmentRepository instructorActivityAssignmentRepository) {
        this.templateRepository = templateRepository;
        this.studentActivityAssignmentRepository = studentActivityAssignmentRepository;
        this.instructorActivityAssignmentRepository = instructorActivityAssignmentRepository;
        this.memberActiveStatusRepository = memberActiveStatusRepository;
        this.objectMapper = objectMapper;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        InputStream inputStream = new ClassPathResource("templates.json").getInputStream();

        List<Template> templates = objectMapper.readValue(
                inputStream, new TypeReference<List<Template>>() {
                });

        for (Template t : templates) {
            Optional.ofNullable(templateRepository.findByName(t.getName())) // name treated as primary key
                    .map(existing -> {
                        existing.setBody(t.getBody());
                        existing.setSubject(t.getSubject());
                        existing.setTemplateType(t.getTemplateType());
                        return templateRepository.save(existing);
                    })
                    .orElseGet(() -> templateRepository.save(t));
        }

        statusTableInit();
    }

    @Transactional
    protected void statusTableInit() {
        Map<Long, List<StudentActivityAssignment>> assignments = studentActivityAssignmentRepository
                .findAllForMemberActiveStatusCache()
                .stream()
                .collect(Collectors.groupingBy(a -> a.getStudent().getId(),
                        LinkedHashMap::new,
                        Collectors.toList()));

        for (Map.Entry<Long, List<StudentActivityAssignment>> entry : assignments.entrySet()) {
            List<ActivePeriod> periods = mergeAssignments(entry.getValue().stream()
                .map(a -> new ActivePeriod(a.getMembershipStartDate(), a.getMembershipEndDate()))
                .collect(Collectors.toList()));
            Member member = entry.getValue().get(0).getStudent();

            MemberActiveStatus status = memberActiveStatusRepository.findById(member.getId()).orElse(null);
            if (status == null) {
                status = new MemberActiveStatus(member, periods);
            } else {
                status.setActivePeriods(periods);
            }
            memberActiveStatusRepository.save(status);
        }

        Map<Long, List<InstructorActivityAssignment>> assignmentss = instructorActivityAssignmentRepository
                .findAllForInstructorActiveStatusCache()
                .stream()
                .collect(Collectors.groupingBy(a -> a.getInstructor().getId(),
                        LinkedHashMap::new,
                        Collectors.toList()));

        for (Map.Entry<Long, List<InstructorActivityAssignment>> entry : assignmentss.entrySet()) {
            List<ActivePeriod> periods = mergeAssignments(entry.getValue().stream()
                .map(a -> new ActivePeriod(a.getStartDate(), a.getEndDate()))
                .collect(Collectors.toList()));

            Member member = entry.getValue().get(0).getInstructor();

            MemberActiveStatus status = memberActiveStatusRepository.findById(member.getId()).orElse(null);
            if (status == null) {
                status = new MemberActiveStatus(member, periods);
            } else {
                status.setActivePeriods(periods);
            }
            memberActiveStatusRepository.save(status);
        }
    }

    private List<ActivePeriod> mergeAssignments(List<ActivePeriod> periods) {
        if (periods.isEmpty()) {
            return periods;
        }

        periods.sort(
                Comparator.comparing(ActivePeriod::getStartDate, Comparator.nullsFirst(Comparator.naturalOrder())));

        List<ActivePeriod> merged = new ArrayList<>();
        ActivePeriod current = new ActivePeriod(periods.get(0).getStartDate(), periods.get(0).getEndDate());

        for (int i = 1; i < periods.size(); i++) {
            ActivePeriod next = periods.get(i);

            if (shouldMerge(current, next)) {
                // Expand the current period to cover both
                current.setEndDate(maxDate(current.getEndDate(), next.getEndDate()));
            } else {
                merged.add(current);
                current = new ActivePeriod(next.getStartDate(), next.getEndDate());
            }
        }
        merged.add(current);

        // Remove expired periods ( endDate < today )
        Date today = DateUtil.getCurrentDateUTC();
        return merged.stream()
                .filter(p -> p.getEndDate() == null || !p.getEndDate().before(today))
                .collect(Collectors.toList());
    }

    private boolean shouldMerge(ActivePeriod current, ActivePeriod next) {
        if (current.getEndDate() == null) {
            return true;
        }

        Date currentEnd = current.getEndDate();
        Date nextStart = next.getStartDate();

        // Overlapping: next starts on or before current ends
        if (!nextStart.after(currentEnd)) {
            return true;
        }

        // Touching / continuous: next starts the day after current ends
        Date dayAfterCurrentEnd = DateUtil.addDays(currentEnd, 1);
        return !nextStart.after(dayAfterCurrentEnd);
    }

    private Date maxDate(Date d1, Date d2) {
        if (d1 == null || d2 == null) {
            return null; // open-ended
        }
        return d2.after(d1) ? d2 : d1;
    }
}
