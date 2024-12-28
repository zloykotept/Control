package com.example.Control.Transaction;

import com.example.Control.Category.Category;
import com.example.Control.Category.CategoryRepository;
import com.example.Control.ResponseMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    final private TransactionRepository transactionRepository;
    final private CategoryRepository categoryRepository;

    public ResponseMessage save(String name, boolean income, int value, UUID catId, Integer userId) {
        try {
            transactionRepository.save(new Transaction(value, name, catId, income, userId));
        } catch (Exception e) {
            return ResponseMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).error("TRANSACTIONS_POST_INTERNAL").build();
        }

        return ResponseMessage.builder().status(HttpStatus.OK).error("OK").build();
    }

    public ResponseMessage delete(UUID id, Integer userId) {
        int modified;
        try {
            modified = transactionRepository.deleteByUuid(id, userId);
        } catch (Exception e) {
            return ResponseMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).error("TRANSACTIONS_DELETE_INTERNAL").build();
        }

        if (modified == 0) return ResponseMessage.builder().status(HttpStatus.NOT_FOUND).error("TRANSACTIONS_DELETE_NOT_FOUND").build();
        return ResponseMessage.builder().status(HttpStatus.OK).error("OK").build();
    }

    public ResponseMessage get(String catIdString, String timePeriod, Integer userId) throws JsonProcessingException {
        if (!timePeriod.equals("week") && !timePeriod.equals("month") && !timePeriod.equals("year")) {
            return ResponseMessage.builder().status(HttpStatus.BAD_REQUEST).error("TRANSACTIONS_BAD_REQUEST").build();
        }

        String transactionsSerialized;
        if (catIdString != null) {
            UUID catId = UUID.fromString(catIdString);

            transactionsSerialized = new ObjectMapper().writeValueAsString(transactionRepository.getForPeriod("1 " + timePeriod, catId, userId));
        } else {
            transactionsSerialized = new ObjectMapper().writeValueAsString(transactionRepository.getAllForPeriod("1 " + timePeriod, userId));
        }

        return ResponseMessage.builder().status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).message(transactionsSerialized).build();
    }
}
