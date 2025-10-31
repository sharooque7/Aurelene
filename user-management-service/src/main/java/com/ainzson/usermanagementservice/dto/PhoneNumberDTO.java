package com.ainzson.usermanagementservice.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor // ✅ Needed for Jackson
public class PhoneNumberDTO {
    private UUID id;

    @NotBlank(message = "Phone number cannot be empty")
    @Pattern(regexp = "^[0-9]{7,15}$", message = "Phone number must be 7–15 digits")
    private String number;

    @NotBlank(message = "Country code cannot be empty")
    @Pattern(regexp = "^\\+[0-9]{1,4}$", message = "Country code must start with + and have 1–4 digits")
    private String countryCode;
}
