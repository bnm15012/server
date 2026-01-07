package com.dancestudio.erp.base;

import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.response.StatusResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;

interface BaseServiceInterface<Entry, ID> {

    ResponseEntity<BaseResponse<Entry>> add(Entry entry);

    ResponseEntity<BaseResponse<Entry>> update(ID id, Entry entry);

    ResponseEntity<Void> delete(ID id);

    ResponseEntity<BaseResponse<Entry>> get(ID id);
}

public abstract class BaseService<Entry, ID> implements BaseServiceInterface<Entry, ID> {

    protected abstract Entry doAdd(Entry entry) throws Exception;

    protected abstract Entry doUpdate(ID id, Entry entry) throws Exception;

    protected abstract void doDelete(ID id) throws Exception;

    protected abstract Entry doGet(ID id) throws Exception;

    // ------------------ Generic ADD --------------------
    public ResponseEntity<BaseResponse<Entry>> add(Entry entry) {
        try {
            Entry saved = doAdd(entry);

            BaseResponse<Entry> response = new BaseResponse<>(
                    Collections.singletonList(saved),
                    new StatusResponse(1, "Added successfully", StatusResponse.Type.SUCCESS, 1));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            e.printStackTrace();
            BaseResponse<Entry> errorResponse = new BaseResponse<>(
                    null,
                    new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));

            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
        }
    }

    // ------------------ Generic UPDATE --------------------
    public ResponseEntity<BaseResponse<Entry>> update(ID id, Entry entry) {
        try {
            Entry updated = doUpdate(id, entry);

            return ResponseEntity.ok(
                    new BaseResponse<>(Collections.singletonList(updated),
                            new StatusResponse(1, "Updated successfully", StatusResponse.Type.SUCCESS, 1)));

        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BaseResponse<>(null,
                            new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new BaseResponse<>(null,
                            new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0)));
        }
    }

    // ------------------ Generic DELETE --------------------
    public ResponseEntity<Void> delete(ID id) {
        try {
            doDelete(id);
            return ResponseEntity.noContent().build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    // ------------------ Generic GET --------------------
    public ResponseEntity<BaseResponse<Entry>> get(ID id) {
        try {
            Entry entry = doGet(id);

            return ResponseEntity.ok(
                    new BaseResponse<>(Collections.singletonList(entry),
                            new StatusResponse(1, "Fetched successfully", StatusResponse.Type.SUCCESS, 1)));
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new BaseResponse<>(Collections.emptyList(),
                            new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0)));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new BaseResponse<>(null,
                            new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0)));
        }
    }
}
