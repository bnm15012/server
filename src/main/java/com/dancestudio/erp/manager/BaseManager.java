package com.dancestudio.erp.manager;

import jakarta.transaction.Transactional;

public interface BaseManager<Input, ID> {

    Input add(Input entry) throws Exception;

    @Transactional
    Input update(ID id, Input entry) throws Exception;

    void delete(ID id) throws Exception;

    Input getById(ID id) throws Exception;
}