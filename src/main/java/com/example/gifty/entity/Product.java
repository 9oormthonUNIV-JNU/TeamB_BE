package com.example.gifty.entity;

import com.example.gifty.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String productName;

    @Column
    private String productImage;

    @Column
    private int price;

    @Column
    private String description;

    @Builder
    public Product(String productName, String productImage, int price, String description) {
        this.productName = productName;
        this.productImage = productImage;
        this.price = price;
        this.description = description;
    }
}
