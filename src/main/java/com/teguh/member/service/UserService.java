package com.teguh.member.service;

import com.teguh.member.dto.UserDto;
import com.teguh.member.model.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);

    void updateUser(Long id,UserDto userDto);

    User findUserByEmail(String email);
}
