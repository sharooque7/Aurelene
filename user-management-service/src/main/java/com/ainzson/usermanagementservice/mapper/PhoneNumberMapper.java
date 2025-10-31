package com.ainzson.usermanagementservice.mapper;

import com.ainzson.usermanagementservice.dto.PhoneNumberDTO;
import com.ainzson.usermanagementservice.entities.PhoneNumber;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Data
public class PhoneNumberMapper {
    public static Set<PhoneNumberDTO> toDto(Set<PhoneNumber> phoneNumbers) {
        Set<PhoneNumberDTO> phoneNumberDTOS = new HashSet<>();
        for (PhoneNumber phoneNumber : phoneNumbers) {
            PhoneNumberDTO dto = new PhoneNumberDTO();
            dto.setId(phoneNumber.getId());
            dto.setCountryCode(phoneNumber.getCountryCode());
            dto.setNumber(phoneNumber.getNumber());
            phoneNumberDTOS.add(dto);
        }
        return phoneNumberDTOS;
    }
    public static Set<PhoneNumber> toEntity(Set<PhoneNumberDTO> phoneNumberDTOS) {
        Set<PhoneNumber> phoneNumbers = new HashSet<>();
        for (PhoneNumberDTO dto : phoneNumberDTOS) {
            PhoneNumber phoneNumber = new PhoneNumber();
            phoneNumber.setCountryCode(dto.getCountryCode());
            phoneNumber.setNumber(dto.getNumber());
            phoneNumbers.add(phoneNumber);
        }
        return phoneNumbers;
    }


}
