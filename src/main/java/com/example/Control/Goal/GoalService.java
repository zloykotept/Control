package com.example.Control.Goal;

import com.example.Control.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GoalService {
    final private GoalRepository goalRepository;

    public ResponseMessage save(String name, int fullValue, Integer userId) {
        try {
            goalRepository.save(new Goal(name, fullValue, userId));
        } catch (DataIntegrityViolationException e) {
            return ResponseMessage.builder().status(HttpStatus.CONFLICT).error("GOALS_ALREADY_EXISTS").build();
        }

        return ResponseMessage.builder().status(HttpStatus.OK).error("OK").build();
    }

    public ResponseMessage delete(UUID id, Integer userId) {
        int modified;
        try {
            modified = goalRepository.deleteById(id, userId);
        } catch (Exception e) {
            return ResponseMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).error("GOALS_DELETE_INTERNAL").build();
        }
        if (modified == 0) return ResponseMessage.builder().status(HttpStatus.NOT_FOUND).error("GOALS_DELETE_NOT_FOUND").build();

        return ResponseMessage.builder().status(HttpStatus.OK).error("OK").build();
    }
}
