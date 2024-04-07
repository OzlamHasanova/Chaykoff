package com.project.Chaykoff.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ContactResponse {
    private String name;
    private String email;
    private String phone;
}
