package com.example.Control.Goal;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "goals")
@NoArgsConstructor
@Getter
@Setter
public class Goal {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID id;
    private String name;
    @Column(name = "full_value")
    private int fullValue;
    @Column(name = "current_value", insertable = false)
    private Integer currentValue;
    @Column(name = "user_id")
    private Integer userId;

    public Goal(String name, int fullValue, Integer userId) {
        this.name = name;
        this.fullValue = fullValue;
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "Goal{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", fullValue=" + fullValue +
                ", currentValue=" + currentValue +
                ", userId=" + userId +
                '}';
    }
}
