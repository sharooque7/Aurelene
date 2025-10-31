package com.ainzson.usermanagementservice.service;

import com.ainzson.usermanagementservice.dto.AddressDTO;
import com.ainzson.usermanagementservice.entities.Address;
import com.ainzson.usermanagementservice.entities.User;
import com.ainzson.usermanagementservice.exception.ResourceNotFoundException;
import com.ainzson.usermanagementservice.mapper.AddressMapper;
import com.ainzson.usermanagementservice.repository.AddressRepository;
import com.ainzson.usermanagementservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Slf4j
public class AddressService implements IAddress{
    private final AddressRepository addressRepository;
    private final UserRepository userRepository;


    @Override
    public Set<AddressDTO> createAddress(Set<AddressDTO> addressDTOS, UUID userId) {
        log.info("➡️ [createAddress] Request to create {} addresses for userId={}", addressDTOS.size(), userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));


        Set<Address> address = AddressMapper.toEntity(addressDTOS);
        address.forEach(addr->{
            addr.setUser(user) ;
            addressRepository.save(addr);
            }
        );
        log.info("Address Created Successfully for userID {}", userId);

        return AddressMapper.toDto(address);
    }


    @Override
    public AddressDTO getAddressById(UUID id) {
        log.info("➡️ [getAddresses] Fetching addresses for id={}", id);
        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));
        return AddressMapper.toSingleDto(address);
    }

    @Override
    public Set<AddressDTO> getAddressesByUser(UUID userId) {
        log.info("➡️ [getAddressesByUser] Fetching addresses for userId={}", userId);

        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Set<Address> addresses = addressRepository.findByUserId(userId);
        return AddressMapper.toDto(addresses);
    }
    @Override
    @Transactional
    public Set<AddressDTO> updateAddress(Set<AddressDTO> addressDTOS , UUID userId) {
        log.info("➡️ [updateAddress] Updating {} addresses for userId={}", addressDTOS.size(), userId);

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Set<Address> addressEntities = addressRepository.findByUserId(userId);

        Set<Address> updatedAddresses = addressEntities.stream()
                .map(existingAddress -> {
                    // Find matching DTO by ID
                    AddressDTO matchingDto = addressDTOS.stream()
                            .filter(dto -> dto.getId() != null && dto.getId().equals(existingAddress.getId()))
                            .findFirst()
                            .orElse(null);

                    if (matchingDto != null) {
                        existingAddress.setStreet(matchingDto.getStreet());
                        existingAddress.setCity(matchingDto.getCity());
                        existingAddress.setState(matchingDto.getState());
                        existingAddress.setCountry(matchingDto.getCountry());
                        existingAddress.setPostalCode(matchingDto.getPostalCode());
                    }

                    existingAddress.setUser(user);

                    return existingAddress;
                })
                .collect(Collectors.toSet());


        updatedAddresses.forEach(addressRepository::save);
        return AddressMapper.toDto(updatedAddresses);

    }

    @Override
    public void deleteAddress(UUID userId, UUID id) {

        log.info("➡️ [deleteAddress] Deleting address id={} for userId={}", id, userId);
        userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));

        Address address = addressRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Address not found with ID: " + id));

        address.setDeleted(true);
        addressRepository.save(address);
        log.info("User marked as deleted: {}", id);

    }



}
