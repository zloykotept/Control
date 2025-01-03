package com.example.Control.Goal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface GoalRepository extends JpaRepository<Goal, UUID> {
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM goals WHERE id = :id AND user_id = :user_id", nativeQuery = true)
    int deleteById(@Param("id") UUID id,
                   @Param("user_id") Integer userId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE goals SET current_value = LEAST(GREATEST(current_value + :change, 0), full_value) WHERE id = :id AND user_id = :user_id", nativeQuery = true)
    int changeById(@Param("id") UUID id,
                   @Param("change") int change,
                   @Param("user_id") Integer userId);

    @Query(value = "SELECT * FROM goals WHERE user_id = :user_id", nativeQuery = true)
    List<Goal> findAllByUser(@Param("user_id") Integer userId);
}
