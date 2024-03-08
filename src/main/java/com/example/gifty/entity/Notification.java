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
@Table(name = "notifications")
public class Notification extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "funding_support_id")
    private Funding_Support fundingSupport;

    @Column
    private boolean isComplete;

    @Builder
    public Notification(Funding_Support fundingSupport, String message, boolean isComplete) {
        this.fundingSupport = fundingSupport;
        this.isComplete = isComplete;
    }

}
