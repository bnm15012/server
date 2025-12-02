package com.dancestudio.erp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.dancestudio.erp.service.BaseService;

public abstract class BaseController<Input, Output, ID> {

    protected abstract BaseService<Input, Output, ID> getService();

    @PostMapping("/add")
    public ResponseEntity<Output> add(@RequestBody Input entry) {
        return getService().add(entry);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Output> update(@PathVariable ID id, @RequestBody Input entry) {
        return getService().update(id, entry);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        return getService().delete(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<Output> get(@PathVariable ID id) {
        return getService().get(id);
    }

    // @GetMapping("/getAll/{id}")
    // public ResponseEntity<Output> getAll(@PathVariable ID id) {
    //     // TODO Auto-generated method stub
    //     throw new UnsupportedOperationException("Unimplemented method 'getService'");
    //     // return getService().getAll(id);
    // }
}
