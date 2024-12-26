package com.example.Control.Category;

import com.example.Control.ResponseMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CategoryService {
    final private CategoryRepository categoryRepository;

    public Optional<ResponseMessage> save(String name, Integer monthLimit, Integer userId) {
        try {
            categoryRepository.save(new Category(name, monthLimit, userId));
        } catch (DataIntegrityViolationException e) {
            return Optional.of(ResponseMessage.builder().status(HttpStatus.CONFLICT).error("CAT_ALREADY_EXISTS").build());
        }
        return Optional.of(ResponseMessage.builder().status(HttpStatus.OK).error("OK").build());
    }

    public Optional<ResponseMessage> delete(String name, Integer userId) {
        int modified;
        try {
            modified = categoryRepository.deleteByName(name, userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.of(ResponseMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).error("CAT_DELETE_INTERNAL").build());
        }

        if (modified == 0) return Optional.of(ResponseMessage.builder().status(HttpStatus.NOT_FOUND).error("CAT_DELETE_NOT_FOUND").build());
        return Optional.of(ResponseMessage.builder().status(HttpStatus.OK).error("OK").build());
    }

    public Optional<ResponseMessage> update(String name, Integer limit, String target, Integer userId) {
        int modified;
        try {
            modified = categoryRepository.updateByName(name, limit, target, userId);
        } catch (DataIntegrityViolationException e) {
            return Optional.of(ResponseMessage.builder().status(HttpStatus.CONFLICT).error("CAT_PUT_CONFLICT").build());
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.of(ResponseMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).error("CAT_PUT_INTERNAL").build());
        }

        if (modified == 0) return Optional.of(ResponseMessage.builder().status(HttpStatus.NOT_FOUND).error("CAT_PUT_NOT_FOUND").build());
        return Optional.of(ResponseMessage.builder().status(HttpStatus.OK).error("OK").build());
    }

    public Optional<ResponseMessage> get(Integer userId) {
        List<Category> responseList;
        try {
            responseList = categoryRepository.findAllByUserId(userId);
        } catch (Exception e) {
            log.error(e.getMessage());
            return Optional.of(ResponseMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).error("CAT_GET_INTERNAL").build());
        }

        String responseListSerialized;
        try {
            responseListSerialized = new ObjectMapper().writeValueAsString(responseList);
        } catch (Exception e) {
            return Optional.of(ResponseMessage.builder().status(HttpStatus.INTERNAL_SERVER_ERROR).error("CAT_GET_INTERNAL").build());
        }
        return Optional.of(ResponseMessage.builder().status(HttpStatus.OK).contentType(MediaType.APPLICATION_JSON).message(responseListSerialized).build());
    }
}
