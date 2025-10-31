package com.ainzson.usermanagementservice.service;

import com.ainzson.usermanagementservice.dto.AddressDTO;
import java.util.Set;
import java.util.UUID;

public interface IAddress {
    Set<AddressDTO> createAddress(Set<AddressDTO> dto, UUID userId);
    AddressDTO getAddressById(UUID id);
    Set<AddressDTO> getAddressesByUser(UUID userId);
    Set<AddressDTO> updateAddress(Set<AddressDTO> dto, UUID userId);
    void deleteAddress(UUID userId, UUID id);
}
