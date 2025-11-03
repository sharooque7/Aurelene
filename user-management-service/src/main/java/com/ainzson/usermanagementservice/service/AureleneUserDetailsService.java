package com.ainzson.usermanagementservice.service;


import com.ainzson.usermanagementservice.entities.User;
import com.ainzson.usermanagementservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AureleneUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    private  final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("Searching user by email {}", username);
        User user = userRepository.findByEmailAndDeletedFalse(username).orElseThrow(() ->
                new UsernameNotFoundException("User details not found for user: " + username));
        List<GrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("admin"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), "{noop}12345", authorities);
    }

}
