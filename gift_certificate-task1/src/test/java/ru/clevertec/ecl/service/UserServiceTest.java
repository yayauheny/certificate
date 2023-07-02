package ru.clevertec.ecl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.UserReadDto;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.exception.ResourceNotFoundException;
import ru.clevertec.ecl.mapper.UserMapper;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.util.Constant;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserService userService;

    private User user;
    private UserReadDto userReadDto;
    private Pageable pageable;
    private Page<User> users;

    @BeforeEach
    void init() {
        user = getUser();
        userReadDto = getUserReadDto();
        pageable = PageRequest.of(Constant.PAGE_NUMBER, Constant.PAGE_SIZE);
        users = new PageImpl<>(Arrays.asList(user));
    }

    @Test
    void findAllTest() {
        when(userRepository.findAll(pageable)).thenReturn(users);
        when(userMapper.mapToDto(user)).thenReturn(userReadDto);

        assertEquals(1, userService.findAll(pageable).size());
    }

    @Test
    void findByIdTest() {
        when(userRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(user));
        when(userMapper.mapToDto(user)).thenReturn(userReadDto);

        assertEquals(userReadDto, userService.findById(Constant.TEST_ID));
    }

    @Test
    void findByIdExceptionTest() {
        when(userRepository.findById(Constant.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.findById(Constant.TEST_ID));
    }

    private User getUser(){
        return User.builder()
                .id(Constant.TEST_ID)
                .username(Constant.TEST_USERNAME)
                .firstname(Constant.TEST_NAME)
                .lastname(Constant.TEST_NAME)
                .tel(Constant.TEST_TEL)
                .address(Constant.TEST_ADDRESS)
                .build();
    }

    private UserReadDto getUserReadDto(){
        return UserReadDto.builder()
                .id(Constant.TEST_ID)
                .username(Constant.TEST_USERNAME)
                .firstname(Constant.TEST_NAME)
                .lastname(Constant.TEST_NAME)
                .tel(Constant.TEST_TEL)
                .address(Constant.TEST_ADDRESS)
                .build();
    }

}