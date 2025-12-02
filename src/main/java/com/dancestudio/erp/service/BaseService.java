package com.dancestudio.erp.service;

import org.springframework.http.ResponseEntity;

public interface BaseService<Input, Output, ID> {

    ResponseEntity<Output> add(Input entry);

    ResponseEntity<Output> update(ID id, Input entry);

    ResponseEntity<Void> delete(ID id);

    ResponseEntity<Output> get(ID id);

    // ResponseEntity<Output> getAll(ID id);
}
