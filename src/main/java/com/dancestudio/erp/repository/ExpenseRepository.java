package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    @Query("SELECT s FROM Expense s WHERE s.studio.id = :studioId and month(s.expenseDate) >= :startMonth and month(s.expenseDate) <= :endMonth")
    List<Expense> findAllByStudioId(@Param("studioId") Long studioId, @Param("startMonth") Long startMonth, @Param("endMonth") Long endMonth);

    @Query("SELECT s FROM Expense s WHERE s.studio.id = :studioId")
    List<Expense> findAllByStudioId(@Param("studioId") Long studioId);

}
