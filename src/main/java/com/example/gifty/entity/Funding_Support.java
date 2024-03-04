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
@Table(name = "fundings_supports")
public class Funding_Support extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "fundingId")
    private Funding funding;

    @ManyToOne
    @JoinColumn(name = "supportId")
    private Support support;

    @Builder
    public Funding_Support(Funding funding, Support support) {
        this.funding = funding;
        this.support = support;
    }
}
