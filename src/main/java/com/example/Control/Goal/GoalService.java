package com.example.Control.Goal;

import com.example.Control.ResponseMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;
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

    public ResponseMessage put(UUID id, int change, Integer userId) {
        int modified;
        try {
            modified = goalRepository.changeById(id, change, userId);
        } catch (Exception e) {
            return ResponseMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).error("GOALS_PUT_INTERNAL").build();
        }
        if (modified == 0) return ResponseMessage.builder().status(HttpStatus.NOT_FOUND).error("GOALS_PUT_NOT_FOUND").build();

        return ResponseMessage.builder().status(HttpStatus.OK).error("OK").build();
    }

    public ResponseMessage get(Integer userId) throws JsonProcessingException {
        List<Goal> goals = goalRepository.findAllByUser(userId);
        String goalsSerialized = new ObjectMapper().writeValueAsString(goals);

        return ResponseMessage.builder().status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).message(goalsSerialized).build();
    }
}
