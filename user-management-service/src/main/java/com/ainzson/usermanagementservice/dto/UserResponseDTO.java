package com.ainzson.usermanagementservice.dto;


import com.ainzson.usermanagementservice.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Builder
@AllArgsConstructor
@NoArgsConstructor // ✅ Needed for Jackson
@Data
public class UserResponseDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private AddressDTO address;
    private List<PhoneNumberDTO> phoneNumbers;
    private Status status;
    private boolean deleted;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Long version;
}

