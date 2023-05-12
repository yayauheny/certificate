package ru.clevertec.certificate.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.certificate.dto.OrderReadDto;
import ru.clevertec.certificate.entity.Order;

@Mapper
public interface OrderMapper {

    OrderReadDto mapToDto(Order order);

}
