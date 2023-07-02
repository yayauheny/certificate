package ru.clevertec.ecl.mapper;

import org.mapstruct.*;
import ru.clevertec.ecl.dto.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.GiftCertificateReadDto;
import ru.clevertec.ecl.dto.GiftCertificateUpdateDto;
import ru.clevertec.ecl.entity.GiftCertificate;

@Mapper
public interface GiftCertificateMapper {

    GiftCertificateReadDto mapToDto(GiftCertificate certificate);

    GiftCertificate mapToEntity(GiftCertificateCreateDto certificate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GiftCertificate update(@MappingTarget GiftCertificate certificate, GiftCertificateCreateDto certificateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GiftCertificate update(@MappingTarget GiftCertificate certificate, GiftCertificateUpdateDto certificateDto);

}
