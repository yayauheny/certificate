package ru.clevertec.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.certificate.dto.UserReadDto;
import ru.clevertec.certificate.exception.ResourceNotFoundException;
import ru.clevertec.certificate.mapper.UserMapper;
import ru.clevertec.certificate.repository.UserRepository;
import ru.clevertec.certificate.util.Constant;

import java.util.List;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;


    public List<UserReadDto> findAll(Pageable pageable) {
        return userRepository.findAll(pageable).stream()
                .map(userMapper::mapToDto)
                .collect(toList());
    }

    public UserReadDto findById(Integer id) {
        return userRepository.findById(id)
                .map(userMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

}
