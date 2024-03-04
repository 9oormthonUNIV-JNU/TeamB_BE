package com.example.gifty.entity;

import com.example.gifty.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "fundings")
public class Funding extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    @Enumerated(EnumType.STRING)
    private Event event;

    @Column
    private LocalDateTime startDate;

    @Column
    private LocalDateTime endDate;

    @Column
    private String message;

    @Column
    private long totalAmount;

    @Builder
    public Funding(User user, Product product, Event event, LocalDateTime startDate, LocalDateTime endDate, String message, long totalAmount) {
        this.user = user;
        this.product = product;
        this.event = event;
        this.startDate = startDate;
        this.endDate = endDate;
        this.message = message;
        this.totalAmount = totalAmount;
    }
}
