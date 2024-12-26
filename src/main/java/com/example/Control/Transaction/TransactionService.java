package com.example.Control.Transaction;

import com.example.Control.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TransactionService {
    final private TransactionRepository transactionRepository;

    public Optional<ResponseMessage> save(String name, boolean income, int value, UUID catId, Integer userId) {
        try {
            transactionRepository.save(new Transaction(value, name, catId, income, userId));
        } catch (Exception e) {
            return Optional.of(ResponseMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).error("TRANSACTIONS_POST_INTERNAL").build());
        }

        return Optional.of(ResponseMessage.builder().status(HttpStatus.OK).error("OK").build());
    }

    public Optional<ResponseMessage> delete(UUID id, Integer userId) {
        int modified;
        try {
            modified = transactionRepository.deleteByUuid(id, userId);
        } catch (Exception e) {
            return Optional.of(ResponseMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).error("TRANSACTIONS_DELETE_INTERNAL").build());
        }

        if (modified == 0) return Optional.of(ResponseMessage.builder().status(HttpStatus.NOT_FOUND).error("TRANSACTIONS_DELETE_NOT_FOUND").build());
        return Optional.of(ResponseMessage.builder().status(HttpStatus.OK).error("OK").build());
    }
}
