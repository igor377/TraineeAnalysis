package com.example.demo.User;

import com.example.demo.Config.TokenService;
import com.example.demo.User.Domain.DTO.CreateUserDTO;
import com.example.demo.User.Domain.DTO.UpdateUserDTO;
import com.example.demo.User.Domain.DTO.UserResponseDTO;
import com.example.demo.User.Domain.Entity.User;
import com.example.demo.User.Domain.Enums.RoleEnum;
import com.example.demo.User.Repository.UserRepository;
import com.example.demo.User.Service.UserService;
import com.example.demo.exceptions.ConflictException;
import com.example.demo.exceptions.ForbiddenException;
import com.example.demo.exceptions.NotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TokenService tokenService;


    @InjectMocks
    private UserService userService;

    @Test
    void shouldCreateUserSucessfully() {
        CreateUserDTO createUserDTO = new CreateUserDTO("teste@teste.com", "123456", "Teste");

        User saveduser = new User(UUID.randomUUID(), createUserDTO.email(), createUserDTO.password(), createUserDTO.name());

        when(userRepository.save(any(User.class))).thenReturn(saveduser);

        UserResponseDTO responseDTO = userService.CreateUser(createUserDTO);

        assertNotNull(responseDTO);
        assertEquals(saveduser.getId(), responseDTO.id());
        assertEquals(saveduser.getEmail(), responseDTO.email());
        assertEquals(saveduser.getName(), responseDTO.name());

        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void shouldNotCreateUserWithExistingEmail() {
        CreateUserDTO createUserDTO = new CreateUserDTO("teste@teste.com", "123456", "Teste");

        User existingUser = new User(UUID.randomUUID(), createUserDTO.email(), createUserDTO.password(), createUserDTO.name());

        when(userRepository.findByEmail(createUserDTO.email())).thenReturn(java.util.Optional.of(existingUser));

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.CreateUser(createUserDTO));

        assertEquals("Email already in use", exception.getMessage());
    }

    @Test
    void shouldReturnAllUsers() {
        User user1 = new User(UUID.randomUUID(), "user1@test.com", "pass1", "User1");
        User user2 = new User(UUID.randomUUID(), "user2@test.com", "pass2", "User2");
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserResponseDTO> users = userService.findAllUsers();

        assertEquals(2, users.size());
        assertEquals(user1.getEmail(), users.get(0).email());
        assertEquals(user2.getEmail(), users.get(1).email());
    }

    @Test
    void shouldReturnUserById() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "user@test.com", "pass", "User");
        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        UserResponseDTO response = userService.findById(id);

        assertNotNull(response);
        assertEquals(id, response.id());
    }

    @Test
    void shouldThrowNotFoundExceptionWhenUserNotFoundById() {
        UUID id = UUID.randomUUID();
        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.findById(id));
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "old@test.com", "pass", "OldName");
        User userLogged = new User(id, "old@test.com", "pass", "OldName");
        userLogged.setRole(RoleEnum.USER);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO("new@test.com", "NewName");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(updateUserDTO.email())).thenReturn(Optional.empty());
        when(userRepository.save(any(User.class))).thenReturn(user);

        UserResponseDTO response = userService.UpdateUser(updateUserDTO, id, userLogged);

        assertEquals(updateUserDTO.email(), response.email());
        assertEquals(updateUserDTO.name(), response.name());
    }

    @Test
    void shouldThrowForbiddenExceptionWhenUpdatingOtherUser() {
        UUID id = UUID.randomUUID();
        UUID otherId = UUID.randomUUID();
        User user = new User(id, "user@test.com", "pass", "User");
        User userLogged = new User(otherId, "other@test.com", "pass", "Other");
        userLogged.setRole(RoleEnum.USER);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO("new@test.com", "NewName");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        assertThrows(ForbiddenException.class, () -> userService.UpdateUser(updateUserDTO, id, userLogged));
    }

    @Test
    void shouldThrowConflictExceptionWhenUpdatingWithExistingEmail() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "user@test.com", "pass", "User");
        User userLogged = new User(id, "user@test.com", "pass", "User");
        userLogged.setRole(RoleEnum.USER);

        UpdateUserDTO updateUserDTO = new UpdateUserDTO("existing@test.com", "NewName");
        User existingUser = new User(UUID.randomUUID(), "existing@test.com", "pass", "Existing");

        when(userRepository.findById(id)).thenReturn(Optional.of(user));
        when(userRepository.findByEmail(updateUserDTO.email())).thenReturn(Optional.of(existingUser));

        assertThrows(ConflictException.class, () -> userService.UpdateUser(updateUserDTO, id, userLogged));
    }

    @Test
    void shouldDeleteUserSuccessfully() {
        UUID id = UUID.randomUUID();
        User user = new User(id, "user@test.com", "pass", "User");
        User userLogged = new User(id, "user@test.com", "pass", "User");
        userLogged.setRole(RoleEnum.USER);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        assertDoesNotThrow(() -> userService.DeleteUser(id, userLogged));
        verify(userRepository, times(1)).delete(user);
    }

    @Test
    void shouldThrowForbiddenExceptionWhenDeletingOtherUser() {
        UUID id = UUID.randomUUID();
        UUID otherId = UUID.randomUUID();
        User user = new User(id, "user@test.com", "pass", "User");
        User userLogged = new User(otherId, "other@test.com", "pass", "Other");
        userLogged.setRole(RoleEnum.USER);

        when(userRepository.findById(id)).thenReturn(Optional.of(user));

        assertThrows(ForbiddenException.class, () -> userService.DeleteUser(id, userLogged));
    }

    @Test
    void shouldThrowNotFoundExceptionWhenDeletingNonexistentUser() {
        UUID id = UUID.randomUUID();
        User userLogged = new User(id, "user@test.com", "pass", "User");
        userLogged.setRole(RoleEnum.USER);

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.DeleteUser(id, userLogged));
    }
}

