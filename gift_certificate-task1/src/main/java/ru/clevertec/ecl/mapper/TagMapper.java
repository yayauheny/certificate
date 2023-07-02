package ru.clevertec.ecl.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import ru.clevertec.ecl.dto.TagCreateDto;
import ru.clevertec.ecl.dto.TagReadDto;
import ru.clevertec.ecl.entity.Tag;

@Mapper
public interface TagMapper {

    TagReadDto mapToDto(Tag tag);

    Tag mapToEntity(TagCreateDto tag);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Tag update(@MappingTarget Tag tag, TagCreateDto tagCreateDto);

}
