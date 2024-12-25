package com.example.Control.Transaction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "transactions")
@NoArgsConstructor
@Getter
@Setter
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String name;
    @Column(name = "income_bool")
    private Boolean incomeBool;
    private Integer value;
    @Column(name = "category_id")
    private UUID categoryId;

    public Transaction(Integer value, String name, UUID categoryId, Boolean incomeBool) {
        this.value = value;
        this.name = name;
        this.categoryId = categoryId;
        this.incomeBool = incomeBool;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", incomeBool=" + incomeBool +
                ", value=" + value +
                ", categoryId=" + categoryId +
                '}';
    }
}