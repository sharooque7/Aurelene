package com.ainzson.usermanagementservice.controller;

import com.ainzson.usermanagementservice.api.CommonResult;
import com.ainzson.usermanagementservice.dto.RoleDto;
import com.ainzson.usermanagementservice.service.RoleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/roles")
public class RoleController {

    private final RoleService roleService;

    @PostMapping("/create")
    public CommonResult<Void> createRole(@RequestBody @Valid RoleDto roleDto) {
        // Implementation goes here
        roleService.createRole(roleDto);
        return CommonResult.success(null);
    }
}
