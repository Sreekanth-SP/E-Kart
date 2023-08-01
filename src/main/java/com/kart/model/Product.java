package com.kart.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "product_prize")
    private String productPrize;

    @Column(name = "product_ratings")
    private String productRatings;

    @Column(name = "product_Desc")
    private String productDesc;
    @Column(name = "product_image")
    private String productImage;
}
