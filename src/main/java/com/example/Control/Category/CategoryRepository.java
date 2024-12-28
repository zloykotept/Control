package com.example.Control.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM categories WHERE name = :name AND user_id = :userId", nativeQuery = true)
    int deleteByName(@Param("name") String name, @Param("userId") Integer userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE categories SET name = COALESCE(:name, name), month_limit = COALESCE(:limit, month_limit) WHERE name = :target AND user_id = :user_id", nativeQuery = true)
    int updateByName(@Param("name") String name, @Param("limit") Integer limit, @Param("target") String nameTarget, @Param("user_id") Integer userId);

    @Query(value = "SELECT * FROM categories WHERE user_id = :user_id", nativeQuery = true)
    List<Category> findAllByUserId(@Param("user_id") Integer userId);

    @Query(value = "SELECT * FROM categories WHERE name = :name AND user_id = :user_id", nativeQuery = true)
    Optional<Category> findByName(@Param("name") String name, @Param("user_id") Integer userId);
}
