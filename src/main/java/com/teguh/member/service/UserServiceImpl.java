package com.teguh.member.service;

import com.teguh.member.dto.UserDto;
import com.teguh.member.model.Role;
import com.teguh.member.model.User;
import com.teguh.member.repository.RoleRepository;
import com.teguh.member.repository.UserRepository;
import com.teguh.member.utils.TbConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(UserDto userDto) {
        Role role = roleRepository.findByName(TbConstants.Roles.USER);

        if (role == null)
            role = roleRepository.save(new Role(TbConstants.Roles.USER));

        User user = new User(
                userDto.getName(),
                userDto.getEmail(),
                passwordEncoder.encode(userDto.getPassword()),
                userDto.getGender(),
                userDto.getDob(),
                userDto.getIc_number(),
                userDto.getPhone_number(),
                List.of(role));
        userRepository.save(user);
    }

    @Override
    public void updateUser(Long id,UserDto userDto) {
        Role role = roleRepository.findByName(TbConstants.Roles.USER);

        if (role == null)
            role = roleRepository.save(new Role(TbConstants.Roles.USER));

        User userToUpdate = userRepository.findById(id).orElseThrow();
        userToUpdate.setName(userDto.getName());
        if(!userDto.getPassword().isEmpty()){
            userToUpdate.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userToUpdate.setGender(userDto.getGender());
        userToUpdate.setDob(userDto.getDob());
        userToUpdate.setIc_number(userDto.getIc_number());
        userToUpdate.setPhone_number(userDto.getPhone_number());
        //userToUpdate.setRoles(List.of(role));
        userRepository.save(userToUpdate);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

