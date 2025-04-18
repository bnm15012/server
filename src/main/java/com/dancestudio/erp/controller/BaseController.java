package com.dancestudio.erp.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BaseController<Input, Output, ID> {

    @PostMapping("/add")
    public abstract ResponseEntity<Output> add(@RequestBody Input entry);

    @PutMapping("/update/{id}")
    public abstract ResponseEntity<Output> update(@PathVariable ID id, @RequestBody Input entry);

    @DeleteMapping("/delete/{id}")
    public abstract ResponseEntity<Void> delete(@PathVariable ID id);

    @GetMapping("/get/{id}")
    public abstract ResponseEntity<Output> get(@PathVariable ID id);

}