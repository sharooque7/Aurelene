package com.ainzson.usermanagementservice.service;

import com.ainzson.usermanagementservice.dto.UserDTO;
import com.ainzson.usermanagementservice.entities.Role;
import com.ainzson.usermanagementservice.enums.RoleType;
import com.ainzson.usermanagementservice.exception.DuplicateResourceException;
import com.ainzson.usermanagementservice.exception.ResourceNotFoundException;
import com.ainzson.usermanagementservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ainzson.usermanagementservice.entities.User;
import com.ainzson.usermanagementservice.enums.Status;
import com.ainzson.usermanagementservice.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

//    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        this.userRepository = userRepository;
//        this.passwordEncoder = passwordEncoder;
//    }

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {

        log.info("Creating user with email: {}", userDTO.getEmail());

        String email = userDTO.getEmail().trim().toLowerCase();
        userDTO.setEmail(email);

        userRepository.findByEmailAndDeletedFalse(email)
                .ifPresent((existingUser) -> {
                    throw new DuplicateResourceException("User with email " + userDTO.getEmail() + " already exists");
                });

        String password = userDTO.getPassword();
        String hashedPassword = passwordEncoder.encode(password);
        userDTO.setPassword(hashedPassword);

        User user = UserMapper.toEntity(userDTO);

        if (user.getStatus() == null) {
            user.setStatus(Status.ACTIVE);
        }

        User createdUser = userRepository.save(user);

        roleService.assignRolesToUser(createdUser.getId(), RoleType.ROLE_USER);

        log.info("User created successfully with ID: {}", user.getId());
        return UserMapper.toDto(new UserDTO(), createdUser);
    }

    @Override
    public UserDTO getUserById(UUID id) {
        log.info("Fetching user by ID: {}", id);
        return userRepository.findById(id)
                .map((user) -> UserMapper.toDto(new UserDTO(), user))
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<UserDTO> getAllUsers() {
        log.info("Fetching all users");
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> UserMapper.toDto(new UserDTO(), user))
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public UserDTO updateUser(UUID id, UserDTO userDTO) {
        log.info("Updating user with ID: {}", id);

        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        if (!existingUser.getEmail().equalsIgnoreCase(userDTO.getEmail())
                && userRepository.existsByEmail(userDTO.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + userDTO.getEmail());
        }

        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setFirstName(userDTO.getFirstName());

        userRepository.save(existingUser);

        log.info("User updated successfully with ID: {}", id);
        return UserMapper.toDto(new UserDTO(),existingUser);
    }

    @Override
    @Transactional
    public void deleteUser(UUID id) {
        log.info("Deleting user with ID: {}", id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        user.setDeleted(true);
        user.setStatus(Status.INACTIVE);
        userRepository.save(user);

        log.info("User marked as deleted: {}", id);
    }


}
