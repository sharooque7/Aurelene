package com.ainzson.usermanagementservice.controller;
import com.ainzson.usermanagementservice.api.CommonResult;
import com.ainzson.usermanagementservice.dto.UserDTO;
import com.ainzson.usermanagementservice.service.IUserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public CommonResult<UserDTO> createUser(@Valid @RequestBody UserDTO userDTO) {
        return CommonResult.success(userService.createUser(userDTO));
    }

    @GetMapping({"/{id}"})
    public CommonResult<UserDTO> getUser(@PathVariable("id") UUID id) {
        return CommonResult.success(userService.getUserById(id));
    }

    @PutMapping("/{id}")
    public CommonResult<UserDTO> updateUser(
            @PathVariable("id") UUID id,
            @RequestBody UserDTO request) {
        return CommonResult.success(userService.updateUser(id, request));
    }

    @DeleteMapping("/{id}")
    public CommonResult<?> deleteUser(@PathVariable("id") UUID id) {
        userService.deleteUser(id);
        return CommonResult.success(null);
    }

    @GetMapping
    public CommonResult<List<UserDTO>> getAllUsers() {
        return CommonResult.success(userService.getAllUsers());
    }

}
