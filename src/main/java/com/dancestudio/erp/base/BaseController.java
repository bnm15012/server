package com.dancestudio.erp.base;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

public abstract class BaseController<Entry, ID> {

    protected abstract BaseService<Entry, ID> getService();

    @PostMapping("/add")
    public ResponseEntity<BaseResponse<Entry>> add(@RequestBody Entry entry) {
        return getService().add(entry);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<BaseResponse<Entry>> update(@PathVariable ID id, @RequestBody Entry entry) {
        return getService().update(id, entry);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> delete(@PathVariable ID id) {
        return getService().delete(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<BaseResponse<Entry>> get(@PathVariable ID id) {
        return getService().get(id);
    }
}
