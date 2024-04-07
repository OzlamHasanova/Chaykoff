package com.project.Chaykoff.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BlogResponse {
    private Long id;
    private String title;
    private String description;
    private String mainImageURL;
}
