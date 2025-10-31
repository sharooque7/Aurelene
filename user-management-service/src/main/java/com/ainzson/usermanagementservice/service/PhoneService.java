package com.ainzson.usermanagementservice.service;

import com.ainzson.usermanagementservice.dto.PhoneNumberDTO;
import com.ainzson.usermanagementservice.entities.PhoneNumber;
import com.ainzson.usermanagementservice.entities.User;
import com.ainzson.usermanagementservice.exception.ResourceNotFoundException;
import com.ainzson.usermanagementservice.mapper.PhoneNumberMapper;
import com.ainzson.usermanagementservice.repository.PhoneRepository;
import com.ainzson.usermanagementservice.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@AllArgsConstructor
public class PhoneService implements IPhoneNumber{

    private final PhoneRepository phoneRepository;
    private final UserRepository userRepository;



    @Override
    public Set<PhoneNumberDTO> createPhoneNumber(Set<PhoneNumberDTO> phoneNumberDTOS, UUID userId) {

        log.info("Creating phone number for user {}", userId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userId
        ));

        Set<PhoneNumber> phoneNumbers = PhoneNumberMapper.toEntity(phoneNumberDTOS);

        phoneNumbers.stream().forEach(pn -> {
            pn.setUser(user);
            phoneRepository.save(pn);
        });
        log.info("Phone number created for user {}", userId);

        return PhoneNumberMapper.toDto(phoneNumbers);



    }

    @Override
    public Set<PhoneNumberDTO> getAllPhoneNumber(UUID userId) {
        log.info("Fetching phone numbers for user {}", userId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userId
                ));
        Set<PhoneNumber> phoneNumbers = phoneRepository.findByUserId(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("Phone numbers not found for user id: " + userId
        ));

        log.info("Fetched {} phone numbers for user {}", phoneNumbers.size(), userId);

        return PhoneNumberMapper.toDto(phoneNumbers);

    }

    @Override
    public Set<PhoneNumberDTO> updatePhoneNumber(Set<PhoneNumberDTO> phoneNumberDTOS, UUID userId) {
        log.info("Updating phone number for user {}", userId);
        User user = userRepository.findById(userId).orElseThrow(
                () -> new ResourceNotFoundException("User not found with id: " + userId
                ));

        Set<PhoneNumber> existingPhoneNumber = phoneRepository.findByUserId(userId).orElseThrow(
                () -> new ResourceNotFoundException("Phone number not found for user id: " + userId
                ));

        Set<PhoneNumber> phoneNumbers = existingPhoneNumber.stream().map(
                existing -> {
                    PhoneNumberDTO phoneNumberDTO = phoneNumberDTOS
                            .stream()
                            .filter(ph -> ph.getId() != null && ph.getId().equals(existing.getId()))
                            .findFirst()
                            .orElse(null);

                    if (phoneNumberDTO != null) {
                        existing.setNumber(phoneNumberDTO.getNumber());
                        existing.setCountryCode(phoneNumberDTO.getNumber());
                    }
                    existing.setUser(user);
                    return existing;
                }).collect(Collectors.toSet());

        phoneNumbers.stream().forEach(ph -> phoneRepository.save(ph));
        log.info("Phone number updated for user {}", userId);
        return PhoneNumberMapper.toDto(phoneNumbers);
    }

    @Override
    public void deletePhoneNumber(UUID phoneNumberId, UUID userId){
        log.info("Deleting phone number {} for user {}", phoneNumberId, userId);
        userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId)
        );

        PhoneNumber phoneNumber = phoneRepository.findById(phoneNumberId).orElseThrow(
                () -> new ResourceNotFoundException("Phone number not found with id: " + phoneNumberId)
        );

        phoneNumber.setDeleted(true);

        phoneRepository.save(phoneNumber);
        log.info("Deleted phone number {} for user {}", phoneNumberId, userId);

    }

}

