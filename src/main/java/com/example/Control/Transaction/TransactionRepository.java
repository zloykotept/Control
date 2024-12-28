package com.example.Control.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM transactions WHERE id = :id AND user_id = :user_id", nativeQuery = true)
    int deleteByUuid(@Param("id") UUID id,
                     @Param("user_id") Integer userId);

    @Query(value = "SELECT * FROM transactions WHERE date >= NOW() - (:period)::INTERVAL AND user_id = :user_id", nativeQuery = true)
    List<Transaction> getAllForPeriod(@Param("period") String period,
                                      @Param("user_id") Integer userId);

    @Query(value = "SELECT * FROM transactions WHERE date >= NOW() - (:period)::INTERVAL AND category_id = :category_id", nativeQuery = true)
    List<Transaction> getForPeriod(@Param("period") String period,
                                   @Param("category_id") UUID catId,
                                   @Param("user_id") Integer userId);

}
