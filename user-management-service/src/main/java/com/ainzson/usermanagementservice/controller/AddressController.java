package com.ainzson.usermanagementservice.controller;

import com.ainzson.usermanagementservice.api.CommonResult;
import com.ainzson.usermanagementservice.dto.AddressDTO;
import com.ainzson.usermanagementservice.service.AddressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/{userId}/addresses")
@RequiredArgsConstructor
@Slf4j
public class AddressController {

    private final AddressService addressService;

    @PostMapping()
    public CommonResult<Set<AddressDTO>> createAddress(@PathVariable("userId") UUID userId, @Valid  @RequestBody Set<AddressDTO> addressDTOS) {        log.info("Creating Address for userID {");
        log.info("Creating Address for userID {}", userId);
        return CommonResult.success((addressService.createAddress(addressDTOS, userId)));
    }

    @GetMapping("/{id}")
    public CommonResult<AddressDTO> getAddressById(@PathVariable("id") UUID id) {
        return CommonResult.success(addressService.getAddressById(id));
    }

    @GetMapping()
    public CommonResult<Set<AddressDTO>> getAddressesByUser(@PathVariable("userId") UUID userId) {
        return CommonResult.success(addressService.getAddressesByUser(userId));
    }

    @PutMapping
    public CommonResult<Set<AddressDTO>> updateAddress(@PathVariable("userId") UUID userId, @RequestBody Set<AddressDTO> addressDTO) {
        return CommonResult.success(addressService.updateAddress(addressDTO, userId));
    }

    @DeleteMapping("/{id}")
    public CommonResult<Void> deleteAddress(@PathVariable("userId") UUID userId, @PathVariable("id") UUID id ) {

        addressService.deleteAddress(userId, id);
        return CommonResult.success(null);
    }
}
