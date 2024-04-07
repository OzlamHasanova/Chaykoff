package com.project.Chaykoff.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ContactRequest {
    private String name;
    private String email;
    private String phone;
}
