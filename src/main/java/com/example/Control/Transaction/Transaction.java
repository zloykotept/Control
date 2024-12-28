package com.example.Control.Transaction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
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
    @Column(name = "date", updatable = false, insertable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Column(name = "category_id")
    private UUID categoryId;
    @Column(name = "user_id")
    private Integer userId;

    public Transaction(Integer value, String name, UUID categoryId, Boolean incomeBool, Integer userId) {
        this.value = value;
        this.name = name;
        this.categoryId = categoryId;
        this.incomeBool = incomeBool;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", incomeBool=" + incomeBool +
                ", value=" + value +
                ", date='" + date + '\'' +
                ", categoryId=" + categoryId +
                ", userId=" + userId +
                '}';
    }
}
