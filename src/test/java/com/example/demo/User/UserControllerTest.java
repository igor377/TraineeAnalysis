package com.example.demo.User;

import com.example.demo.Config.TokenService;
import com.example.demo.User.Controller.UserController;
import com.example.demo.User.Domain.DTO.CreateUserDTO;
import com.example.demo.User.Domain.DTO.UpdateUserDTO;
import com.example.demo.User.Domain.DTO.UserResponseDTO;

import com.example.demo.User.Service.UserService;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.ForbiddenException;
import com.example.demo.exceptions.NotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @InjectMocks
    private UserService userService;

    @InjectMocks
    private TokenService tokenService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateUser() throws Exception {
        CreateUserDTO dto = new CreateUserDTO("user@test.com", "User", "pass");
        UserResponseDTO response = new UserResponseDTO(UUID.randomUUID(), "user@test.com", "User");

        Mockito.when(userService.CreateUser(any())).thenReturn(response);

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(dto.email()));
    }

    @Test
    void shouldReturnConflictWhenEmailExists() throws Exception {
        CreateUserDTO dto = new CreateUserDTO("user@test.com", "User", "pass");

        Mockito.when(userService.CreateUser(any())).thenThrow(new ConflictException("Email already in use"));

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isConflict());
    }

    @Test
    void shouldGetAllUsers() throws Exception {
        UserResponseDTO user1 = new UserResponseDTO(UUID.randomUUID(), "a@a.com", "A");
        UserResponseDTO user2 = new UserResponseDTO(UUID.randomUUID(), "b@b.com", "B");

        Mockito.when(userService.findAllUsers()).thenReturn(List.of(user1, user2));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldGetUserById() throws Exception {
        UUID id = UUID.randomUUID();
        UserResponseDTO user = new UserResponseDTO(id, "a@a.com", "A");

        Mockito.when(userService.findById(id)).thenReturn(user);

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id.toString()));
    }

    @Test
    void shouldReturnNotFoundWhenUserDoesNotExist() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.when(userService.findById(id)).thenThrow(new NotFoundException("User not found"));

        mockMvc.perform(get("/users/{id}", id))
                .andExpect(status().isNotFound());
    }

    @Test
    void shouldUpdateUser() throws Exception {
        UUID id = UUID.randomUUID();
        UpdateUserDTO dto = new UpdateUserDTO("new@a.com", "NewName");
        UserResponseDTO response = new UserResponseDTO(id, "new@a.com", "NewName");

        Mockito.when(userService.UpdateUser(any(), eq(id), any())).thenReturn(response);

        mockMvc.perform(put("/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(dto.email()));
    }

    @Test
    void shouldReturnForbiddenWhenUpdateNotAllowed() throws Exception {
        UUID id = UUID.randomUUID();
        UpdateUserDTO dto = new UpdateUserDTO("new@a.com", "NewName");

        Mockito.when(userService.UpdateUser(any(), eq(id), any())).thenThrow(new ForbiddenException("You can only update your own profile"));

        mockMvc.perform(put("/users/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldDeleteUser() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doNothing().when(userService).DeleteUser(eq(id), any());

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isNoContent());
    }

    @Test
    void shouldReturnForbiddenWhenDeleteNotAllowed() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doThrow(new ForbiddenException("You can only delete your own profile"))
                .when(userService).DeleteUser(eq(id), any());

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturnNotFoundWhenDeleteNonexistentUser() throws Exception {
        UUID id = UUID.randomUUID();

        Mockito.doThrow(new NotFoundException("User not found"))
                .when(userService).DeleteUser(eq(id), any());

        mockMvc.perform(delete("/users/{id}", id))
                .andExpect(status().isNotFound());
    }
}