package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.UserReadDto;
import ru.clevertec.ecl.entity.User;

@Mapper
public interface UserMapper {

    UserReadDto mapToDto(User user);

}
