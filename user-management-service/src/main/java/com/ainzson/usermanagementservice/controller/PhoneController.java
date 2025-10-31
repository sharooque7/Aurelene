package com.ainzson.usermanagementservice.controller;

import com.ainzson.usermanagementservice.api.CommonResult;
import com.ainzson.usermanagementservice.dto.PhoneNumberDTO;
import com.ainzson.usermanagementservice.service.PhoneService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/{userId}/phone")
@RequiredArgsConstructor
@Slf4j
public class PhoneController {

    public final PhoneService phoneService;

    @PostMapping()
    public CommonResult<Set<PhoneNumberDTO>> createAddress(@PathVariable("userId") UUID userId, @Valid @RequestBody Set<PhoneNumberDTO> phoneNumberDTOS) {        log.info("Creating Address for userID {");
        log.info("Creating Address for userID {}", userId);
        return CommonResult.success((phoneService.createPhoneNumber(phoneNumberDTOS, userId)));
    }

    @GetMapping()
    public CommonResult<Set<PhoneNumberDTO>> getAddressesByUser(@PathVariable("userId") UUID userId) {
        return CommonResult.success(phoneService.getAllPhoneNumber(userId));
    }

    @PutMapping
    public CommonResult<Set<PhoneNumberDTO>> updateAddress(@PathVariable("userId") UUID userId, @RequestBody Set<PhoneNumberDTO> phoneNumberDTOS) {
        return CommonResult.success(phoneService.updatePhoneNumber(phoneNumberDTOS, userId));
    }

    @DeleteMapping("/{id}")
    public CommonResult<Void> deleteAddress(@PathVariable("userId") UUID userId, @PathVariable("id") UUID id ) {

        phoneService.deletePhoneNumber(userId, id);
        return CommonResult.success(null);
    }
}
