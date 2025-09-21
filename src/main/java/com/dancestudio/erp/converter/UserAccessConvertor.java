package com.dancestudio.erp.converter;

import java.util.Objects;

import org.springframework.stereotype.Component;

import com.dancestudio.erp.entity.User;
import com.dancestudio.erp.entity.UserAccess;
import com.dancestudio.erp.entry.UserAccessEntry;
import com.dancestudio.erp.enums.AccessLevel;

@Component
public class UserAccessConvertor {

    public static UserAccessEntry convertToEntry(UserAccess userAccess)
            throws Exception {
        UserAccessEntry newAccessEntry = new UserAccessEntry();
        newAccessEntry.setActivity(userAccess.getActivity());
        newAccessEntry.setCommunication(userAccess.getCommunication());
        newAccessEntry.setPayments(userAccess.getPayments());
        newAccessEntry.setExpense(userAccess.getExpense());
        newAccessEntry.setAnalysis(userAccess.getAnalysis());
        newAccessEntry.setReports(userAccess.getReports());
        newAccessEntry.setEnquiry(userAccess.getEnquiry());
        return newAccessEntry;
    }

    public static UserAccess convertToEntity(UserAccessEntry accessEntry,
            UserAccess existingAccessEntry) throws Exception {
        UserAccess newUserAccess = (existingAccessEntry != null) ? existingAccessEntry
                : new UserAccess();

        if (Objects.nonNull(accessEntry.getActivity())) {
            newUserAccess.setActivity(accessEntry.getActivity());
        }
        if (Objects.nonNull(accessEntry.getCommunication())) {
            newUserAccess.setCommunication(accessEntry.getCommunication());
        }
        if (Objects.nonNull(accessEntry.getPayments())) {
            newUserAccess.setPayments(accessEntry.getPayments());
        }
        if (Objects.nonNull(accessEntry.getExpense())) {
            newUserAccess.setExpense(accessEntry.getExpense());
        }
        if (Objects.nonNull(accessEntry.getAnalysis())) {
            newUserAccess.setAnalysis(accessEntry.getAnalysis());
        }
        if (Objects.nonNull(accessEntry.getReports())) {
            newUserAccess.setReports(accessEntry.getReports());
        }
        if (Objects.nonNull(accessEntry.getEnquiry())) {
            newUserAccess.setEnquiry(accessEntry.getEnquiry());
        }
        return newUserAccess;
    }

    public static UserAccess defaultAccessLevel(Boolean full, User user) {
        UserAccess userAccess = user.getUserAccess() != null ? user.getUserAccess() : new UserAccess();
        AccessLevel defAccessLevel = full ? AccessLevel.FULL : AccessLevel.NONE;
        userAccess.setUser(user);
        userAccess.setActivity(defAccessLevel);
        userAccess.setCommunication(AccessLevel.FULL);
        userAccess.setPayments(AccessLevel.FULL);
        userAccess.setExpense(AccessLevel.FULL);
        userAccess.setAnalysis(defAccessLevel);
        userAccess.setReports(defAccessLevel);
        userAccess.setEnquiry(defAccessLevel);
        return userAccess;
    }
}
