package com.project.Chaykoff.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


public record ProductAllResponse(
        List<ProductResponse> productResponseList,
        int totalPages,
        long totalElements,
        boolean hasNext
) {
}