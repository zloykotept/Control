package com.example.Control.Category;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "categories")
@NoArgsConstructor
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String name;
    @Column(name = "month_limit")
    private Integer monthLimit;
    @Column(name = "user_id")
    private Integer userId;

    public Category(String name, Integer monthLimit, Integer userId) {
        this.name = name;
        this.monthLimit = monthLimit;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", monthLimit=" + monthLimit +
                ", userId=" + userId +
                '}';
    }
}
