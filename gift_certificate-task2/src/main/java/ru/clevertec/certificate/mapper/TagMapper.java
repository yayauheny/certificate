package ru.clevertec.certificate.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.certificate.dto.TagCreateDto;
import ru.clevertec.certificate.dto.TagReadDto;
import ru.clevertec.certificate.entity.Tag;

@Mapper
public interface TagMapper {

    TagReadDto mapToDto(Tag tag);

    Tag mapToEntity(TagCreateDto tag);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tag update(@MappingTarget Tag tag, TagCreateDto tagCreateDto);

}
