package com.ainzson.usermanagementservice.service;

import com.ainzson.usermanagementservice.dto.PhoneNumberDTO;

import java.util.Set;
import java.util.UUID;

public interface IPhoneNumber {
    Set<PhoneNumberDTO> createPhoneNumber(Set<PhoneNumberDTO> phoneNumberDTOS, UUID userId);
    Set<PhoneNumberDTO> getAllPhoneNumber(UUID userId);
    Set<PhoneNumberDTO> updatePhoneNumber(Set<PhoneNumberDTO> phoneNumberDTOS, UUID userId);
    void deletePhoneNumber(UUID phoneNumberId, UUID userId);
}
