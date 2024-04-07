package com.project.Chaykoff.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductResponse {
    private Long id;
    private String name;
    private String title;
    private Double price;
    private String mainImageURL;
    private Set<String> imageURLs = new HashSet<>();
    private Set<String> ingredients = new HashSet<>();


}
