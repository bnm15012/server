package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Template;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface TemplateRepository extends JpaRepository<Template, Long> {

    @Query(value = "select * from template where name = :templateName", nativeQuery = true)
    Template findByName(String templateName);

}