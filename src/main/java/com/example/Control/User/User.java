package com.example.Control.User;

import jakarta.persistence.*;
import org.hibernate.annotations.ColumnDefault;

import java.util.Arrays;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private byte[] password;
    private Integer balance;

    @PrePersist
    public void prePersist() {
        if (this.balance == null) this.balance = 0;
    }

    public User() {
    }

    public User(String name, byte[] password) {
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public Integer getBalance() {
        return balance;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", password=" + Arrays.toString(password) +
                ", balance=" + balance +
                '}';
    }
}
