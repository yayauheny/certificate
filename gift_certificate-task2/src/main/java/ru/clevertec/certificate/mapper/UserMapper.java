package ru.clevertec.certificate.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.certificate.dto.UserReadDto;
import ru.clevertec.certificate.entity.User;

@Mapper
public interface UserMapper {

    UserReadDto mapToDto(User user);

}
