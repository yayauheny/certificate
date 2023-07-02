package ru.clevertec.ecl.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.ecl.dto.OrderReadDto;
import ru.clevertec.ecl.entity.Order;

@Mapper
public interface OrderMapper {

    OrderReadDto mapToDto(Order order);

}
