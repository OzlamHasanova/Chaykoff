package com.project.Chaykoff.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long id;

    private String name;
    private String title;


    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "main_image_id")
    private Image mainImage;

    private Double price;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_images"
    )
    private Set<Image> images = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(
            name = "product_ingredients", // Update the join table name if needed
            joinColumns = @JoinColumn(name = "product_id"), // Column(s) in the join table referencing Product
            inverseJoinColumns = @JoinColumn(name = "ingredient_id") // Column(s) in the join table referencing Ingredient
    )
    private Set<Ingredient> ingredients = new HashSet<>();



}
