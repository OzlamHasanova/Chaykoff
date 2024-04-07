package com.project.Chaykoff.dto.response;

import lombok.Data;

import java.util.List;

public record BlogAllResponse (
    List<BlogResponse> blogResponseList,
    int totalPages,
    long totalElements,
    boolean hasNext
) {
    }
