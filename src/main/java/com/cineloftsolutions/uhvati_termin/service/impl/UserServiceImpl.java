package com.cineloftsolutions.uhvati_termin.service.impl;

import com.cineloftsolutions.uhvati_termin.dto.UserDTO;
import com.cineloftsolutions.uhvati_termin.entity.User;
import com.cineloftsolutions.uhvati_termin.exception.ConflictException;
import com.cineloftsolutions.uhvati_termin.maper.UserMapper;
import com.cineloftsolutions.uhvati_termin.repository.UserRepository;
import com.cineloftsolutions.uhvati_termin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    @Transactional(readOnly = true)
    public List<UserDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .collect(Collectors.toList());
    }
}

