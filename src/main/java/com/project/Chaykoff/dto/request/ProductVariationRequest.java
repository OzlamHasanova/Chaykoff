package com.project.Chaykoff.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariationRequest {
    private Double price;
    private Double discount;
    private Integer stockQuantity;
}
