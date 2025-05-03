package com.dancestudio.erp.manager.impl;

import com.dancestudio.erp.entity.Attendance;
import com.dancestudio.erp.entry.AttendanceEntry;
import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.manager.AttendanceManager;
import com.dancestudio.erp.manager.StudioManager;
import com.dancestudio.erp.repository.AttendanceRepository;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class AttendanceManagerImpl implements AttendanceManager {
    private final AttendanceRepository attendanceRepository;

    @Autowired private StudioManager studioManager;

    @Autowired
    public AttendanceManagerImpl(AttendanceRepository attendanceRepository) {
        this.attendanceRepository = attendanceRepository;
    }

    @Override
    public AttendanceEntry add(AttendanceEntry attendanceEntry) throws EntityNotFoundException {
        Attendance attendance = convertToEntity(attendanceEntry, null);
        return convertToEntry(attendanceRepository.save(attendance));
    }

    @Override
    public AttendanceEntry update(Long attendanceId, AttendanceEntry attendanceEntry) throws EntityNotFoundException {
        Attendance existingAttendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new EntityNotFoundException("Attendance not found"));

        Attendance updatedAttendance = convertToEntity(attendanceEntry, existingAttendance);
        return convertToEntry(attendanceRepository.save(updatedAttendance));
    }

    @Override
    public void delete(Long attendanceId) throws EntityNotFoundException {
        attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new EntityNotFoundException("Attendance not found"));

        attendanceRepository.deleteById(attendanceId);
    }

    @Override
    public AttendanceEntry getById(Long attendanceId) throws EntityNotFoundException {
        Attendance attendance = attendanceRepository.findById(attendanceId)
                .orElseThrow(() -> new EntityNotFoundException("Attendance not found"));

        return convertToEntry(attendance);
    }

    @Override
    public List<AttendanceEntry> getAllAttendances(HttpServletRequest request) throws EntityNotFoundException {

        List<Attendance> attendances = attendanceRepository.findAll();

        List<AttendanceEntry> attendanceEntries = new ArrayList<>();
        for (Attendance attendance : attendances) {
            AttendanceEntry attendanceEntry = convertToEntry(attendance);
            attendanceEntries.add(attendanceEntry);
        }
        return attendanceEntries;
    }


    public AttendanceEntry convertToEntry(Attendance attendance) throws EntityNotFoundException {

        AttendanceEntry attendanceEntry = new AttendanceEntry();
        attendanceEntry.setId(attendance.getId());
        return attendanceEntry;
    }

    private Attendance convertToEntity(AttendanceEntry attendanceEntry, Attendance existingAttendance) throws EntityNotFoundException {

        Attendance attendance = (existingAttendance != null) ? existingAttendance : new Attendance();
        if (Objects.nonNull(attendanceEntry.getId())) {
            attendance.setId(attendanceEntry.getId());
        }

        return attendance;
    }

}
