package com.example.Control.Transaction;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

public interface TransactionRepository extends JpaRepository<Transaction, UUID> {

    @Transactional
    @Modifying
    @Query(value = "DELETE FROM transactions WHERE id = :id AND user_id = :user_id", nativeQuery = true)
    int deleteByUuid(@Param("id") UUID id, @Param("user_id") Integer userId);

}
