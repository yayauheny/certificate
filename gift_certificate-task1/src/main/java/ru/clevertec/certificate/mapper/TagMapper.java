package ru.clevertec.certificate.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.certificate.dto.TagCreateDto;
import ru.clevertec.certificate.dto.TagReadDto;
import ru.clevertec.certificate.entity.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {

    TagReadDto mapToDto(Tag tag);

    Tag mapToEntity(TagCreateDto tag);

}
