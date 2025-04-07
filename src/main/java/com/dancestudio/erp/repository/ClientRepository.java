package com.dancestudio.erp.repository;

import com.dancestudio.erp.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {

}