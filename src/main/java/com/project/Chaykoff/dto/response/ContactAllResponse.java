package com.project.Chaykoff.dto.response;

import java.util.List;

public record ContactAllResponse(
        List<ContactResponse> contactResponseList,
        int totalPages,
        long totalElements,
        boolean hasNext
) {
}