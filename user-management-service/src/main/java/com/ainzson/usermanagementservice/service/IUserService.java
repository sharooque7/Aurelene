package com.ainzson.usermanagementservice.service;

import com.ainzson.usermanagementservice.dto.UserDTO;

import java.util.List;
import java.util.UUID;

public interface IUserService {
    UserDTO getUserById(UUID userId);

    UserDTO createUser(UserDTO requestDTO) ;

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UUID id, UserDTO userDTO);

    void deleteUser(UUID id);


}
