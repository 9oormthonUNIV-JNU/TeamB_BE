package com.example.gifty.entity;

import com.example.gifty.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "supports")
public class Support extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column
    private long amount;

    @Column
    private String message;

    @Builder
    public Support(User user, long amount, String message) {
        this.user = user;
        this.amount = amount;
        this.message = message;
    }
}
