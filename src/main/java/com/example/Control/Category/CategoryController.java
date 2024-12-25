package com.example.Control.Category;

import com.example.Control.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class CategoryController {
    final private CategoryService categoryService;

    @PostMapping
    public ResponseEntity<String> postCategory(@RequestParam(name = "name", required = false) String name, @RequestParam(name = "limit", required = false) String limitString) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer)authentication.getPrincipal();
        Integer limit = null;
        try {
            if (limitString != null) limit = Integer.parseInt(limitString);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CAT_BAD_REQUEST");
        }
        if (name == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CAT_BAD_REQUEST");

        ResponseMessage response = categoryService.save(name, limit, userId).orElseThrow();
        return ResponseEntity.status(response.getStatus()).body(response.getError());
    }

    @DeleteMapping
    public ResponseEntity<String> deleteCategory(@RequestParam(name = "name", required = false) String name) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Integer userId = (Integer)authentication.getPrincipal();
        if (name == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("CAT_BAD_REQUEST");

        ResponseMessage response = categoryService.delete(name, userId).orElseThrow();
        return ResponseEntity.status(response.getStatus()).body(response.getError());
    }
}
