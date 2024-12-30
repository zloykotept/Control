package com.example.Control.Goal;

import com.example.Control.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "goals")
@RequiredArgsConstructor
public class GoalController {
    final private GoalService goalService;

    @PostMapping
    public ResponseEntity<String> postGoal(@RequestParam(value = "name", required = false) String name,
                                           @RequestParam(value = "full_value", required = false) String fullValueString) {
        if (name == null || fullValueString == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GOALS_BAD_REQUEST");
        int fullValue;
        try {
            fullValue = Integer.parseInt(fullValueString);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GOALS_BAD_REQUEST");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();

        ResponseMessage response = goalService.save(name, fullValue, userId);
        return ResponseEntity.status(response.getStatus()).body(response.getError());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteGoal(@RequestParam(value = "id", required = false) String idString) {
        if (idString == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GOALS_BAD_REQUEST");

        UUID id;
        try {
            id = UUID.fromString(idString);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("GOALS_BAD_REQUEST");
        }

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer) authentication.getPrincipal();

        ResponseMessage response = goalService.delete(id, userId);
        return ResponseEntity.status(response.getStatus()).body(response.getError());
    }
}
