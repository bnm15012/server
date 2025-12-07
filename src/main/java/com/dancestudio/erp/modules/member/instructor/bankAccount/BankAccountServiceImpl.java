package com.dancestudio.erp.modules.member.instructor.bankAccount;


import com.dancestudio.erp.exception.EntityNotFoundException;
import com.dancestudio.erp.response.StatusResponse;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Setter(onMethod = @__({@Autowired}))
@Component
public class BankAccountServiceImpl implements BankAccountService {

    private BankAccountManager bankAccountManager;

    @Override
    public ResponseEntity<BankAccountResponse> add(BankAccountEntry bankAccountEntry) {
        BankAccountResponse response = new BankAccountResponse();

        try {
            BankAccountEntry entry = bankAccountManager.add(bankAccountEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Bank Account added successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<BankAccountResponse> update(Long bankAccountId, BankAccountEntry bankAccountEntry) {
        BankAccountResponse response = new BankAccountResponse();

        try {
            BankAccountEntry entry = bankAccountManager.update(bankAccountId, bankAccountEntry);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Bank Account updated successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Override
    public ResponseEntity<Void> delete(Long bankAccountId) {
        try {
            bankAccountManager.delete(bankAccountId);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<BankAccountResponse> get(Long bankAccountId) {
        BankAccountResponse response = new BankAccountResponse();

        try {
            BankAccountEntry entry = bankAccountManager.getById(bankAccountId);

            response.setData(Collections.singletonList(entry));
            response.setStatus(new StatusResponse(1, "Bank Account retrieved successfully", StatusResponse.Type.SUCCESS, 1));
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } catch (EntityNotFoundException e) {
            response.setData(Collections.emptyList());
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            response.setStatus(new StatusResponse(0, e.getMessage(), StatusResponse.Type.ERROR, 0));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
