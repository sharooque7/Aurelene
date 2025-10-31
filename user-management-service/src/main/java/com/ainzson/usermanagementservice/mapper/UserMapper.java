package com.ainzson.usermanagementservice.mapper;

import com.ainzson.usermanagementservice.dto.AddressDTO;
import com.ainzson.usermanagementservice.dto.PhoneNumberDTO;
import com.ainzson.usermanagementservice.dto.UserDTO;
import com.ainzson.usermanagementservice.entities.Address;
import com.ainzson.usermanagementservice.entities.PhoneNumber;
import com.ainzson.usermanagementservice.entities.User;

import java.util.Set;


public class UserMapper {

    public static UserDTO toDto(UserDTO userRequestDTO, User user) {
        Set<AddressDTO> addressDTOS = AddressMapper.toDto(user.getAddresses());
        Set<PhoneNumberDTO>  phoneNumberDTOS = PhoneNumberMapper.toDto(user.getPhoneNumbers());

        userRequestDTO.setFirstName(user.getFirstName());
        userRequestDTO.setLastName(user.getLastName());
        userRequestDTO.setEmail(user.getEmail());
        userRequestDTO.setAddress(addressDTOS);
        userRequestDTO.setPhoneNumbers(phoneNumberDTOS);
        return  userRequestDTO;
    }

    public static User toEntity(UserDTO userRequestDTO) {
        Set<Address> addresses = AddressMapper.toEntity(userRequestDTO.getAddress());
        Set<PhoneNumber> phoneNumbers = PhoneNumberMapper.toEntity(userRequestDTO.getPhoneNumbers());
        User user = new User();
        user.setFirstName(userRequestDTO.getFirstName());
        user.setLastName(userRequestDTO.getLastName());
        user.setEmail(userRequestDTO.getEmail());
        user.setAddresses(addresses);
        user.setPhoneNumbers(phoneNumbers);
        return user;

    }

}
