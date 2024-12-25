package com.example.Control.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM categories WHERE name = :name AND user_id = :userId", nativeQuery = true)
    int deleteByName(@Param("name") String name, @Param("userId") Integer userId);

}
