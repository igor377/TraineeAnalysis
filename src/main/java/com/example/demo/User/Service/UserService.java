package com.example.demo.User.Service;

import com.example.demo.User.Domain.DTO.UpdateUserDTO;
import com.example.demo.User.Domain.DTO.UserResponseDTO;
import com.example.demo.User.Domain.Entity.User;
import com.example.demo.User.Domain.Enums.RoleEnum;
import com.example.demo.User.Repository.UserRepository;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.ForbiddenException;
import com.example.demo.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.example.demo.User.Domain.DTO.CreateUserDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserResponseDTO CreateUser(CreateUserDTO createUserDTO) {
        if (userRepository.findByEmail(createUserDTO.email()).isPresent()) {
            throw new ConflictException("Email already in use");
        }
        User user = new User();
        user.setEmail(createUserDTO.email());
        user.setName(createUserDTO.name());
        user.setPassword(passwordEncoder.encode(createUserDTO.password()));
        User savedUser = userRepository.save(user);
        return new UserResponseDTO(savedUser);
    }

    public List<UserResponseDTO> findAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    public UserResponseDTO findById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found"));
        return new UserResponseDTO(user);
    }

    public UserResponseDTO UpdateUser(UpdateUserDTO data, UUID id, User userlogged) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        boolean isAdmin = userlogged.getRole() == RoleEnum.ADMIN;
        if (!isAdmin && !userlogged.getId().equals(id)) {
            throw new ForbiddenException("You can only update your own profile");
        }

        if (data.email() != null) {
            var existingUser = userRepository.findByEmail(data.email());
            if (existingUser.isPresent() && !existingUser.get().getId().equals(id)) {
                throw new ConflictException("Email already in use");
            }
            user.setEmail(data.email());
        }
        if (data.name() != null) {
            user.setName(data.name());
        }

        User updatedUser = userRepository.save(user);
        return new UserResponseDTO(updatedUser);
    }

    public void DeleteUser(UUID id, User userlogged) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        boolean isAdmin = userlogged.getRole() == RoleEnum.ADMIN;
        if (!isAdmin && !userlogged.getId().equals(id)) {
            throw new ForbiddenException("You can only delete your own profile");
        }
        userRepository.delete(user);
    }
}
