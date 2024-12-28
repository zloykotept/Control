package com.example.Control.Transaction;

import com.example.Control.ResponseMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/transactions")
@RequiredArgsConstructor
public class TransactionController {
    final private TransactionService transactionService;

    @PostMapping
    public ResponseEntity<String> postTransaction(@RequestParam(value = "name", required = false) String name,
                                                  @RequestParam(value = "income", required = false) String incomeString,
                                                  @RequestParam(value = "value", required = false) String valueString,
                                                  @RequestParam(value = "category", required = false) String catIdString) {
        if (name == null || incomeString == null || valueString == null || catIdString == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TRANSACTIONS_BAD_REQUEST");
        }

        boolean income;
        int value;
        UUID catId;
        try {
            income = Boolean.parseBoolean(incomeString);
            value = Integer.parseInt(valueString);
            catId = UUID.fromString(catIdString);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TRANSACTIONS_BAD_REQUEST");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();

        ResponseMessage response = transactionService.save(name, income, value, catId, userId);
        return ResponseEntity.status(response.getStatus()).body(response.getError());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteTransaction(@RequestParam(value = "id", required = false) String idString) {
        if (idString == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TRANSACTIONS_BAD_REQUEST");

        UUID id;
        try {
            id = UUID.fromString(idString);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TRANSACTIONS_BAD_REQUEST");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();

        ResponseMessage response = transactionService.delete(id, userId);
        return ResponseEntity.status(response.getStatus()).body(response.getError());
    }

    @GetMapping
    public ResponseEntity<String> getTransactions(@RequestParam(value = "category", required = false) String catIdString,
                                                  @RequestParam(value = "time", required = false) String timePeriod) throws JsonProcessingException {
        if (timePeriod == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("TRANSACTIONS_BAD_REQUEST");

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();

        ResponseMessage response = transactionService.get(catIdString, timePeriod, userId);
        if (response.getError() != null) return ResponseEntity.status(response.getStatus()).body(response.getError());
        return ResponseEntity.status(response.getStatus()).contentType(response.getContentType()).body(response.getMessage());
    }
}
