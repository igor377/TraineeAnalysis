package com.example.demo.User.Controller;

import com.example.demo.User.Domain.DTO.CreateUserDTO;
import com.example.demo.User.Domain.DTO.UpdateUserDTO;
import com.example.demo.User.Domain.DTO.UserResponseDTO;
import com.example.demo.User.Domain.Entity.User;
import com.example.demo.User.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> create(@RequestBody @Valid CreateUserDTO data) {
        UserResponseDTO user = userService.CreateUser(data);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        List<UserResponseDTO> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UserResponseDTO> findById(@PathVariable UUID id) {
        UserResponseDTO userResponseDTO = userService.findById(id);
        return ResponseEntity.ok(userResponseDTO);
    }

    @PatchMapping("/update/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<UserResponseDTO> update(@RequestBody @Valid UpdateUserDTO data, @PathVariable UUID id, @AuthenticationPrincipal User userlogged) {
        UserResponseDTO user = userService.UpdateUser(data, id, userlogged);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Void> delete(@PathVariable UUID id, @AuthenticationPrincipal User userlogged) {
        userService.DeleteUser(id, userlogged);
        return ResponseEntity.noContent().build();
    }
}
