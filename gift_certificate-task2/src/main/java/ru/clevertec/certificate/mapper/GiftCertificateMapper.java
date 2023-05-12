package ru.clevertec.certificate.mapper;

import org.mapstruct.*;
import ru.clevertec.certificate.dto.GiftCertificateCreateDto;
import ru.clevertec.certificate.dto.GiftCertificateReadDto;
import ru.clevertec.certificate.dto.GiftCertificateUpdateDto;
import ru.clevertec.certificate.entity.GiftCertificate;

@Mapper
public interface GiftCertificateMapper {

    GiftCertificateReadDto mapToDto(GiftCertificate certificate);

    GiftCertificate mapToEntity(GiftCertificateCreateDto certificate);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GiftCertificate update(@MappingTarget GiftCertificate certificate, GiftCertificateCreateDto certificateDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    GiftCertificate update(@MappingTarget GiftCertificate certificate, GiftCertificateUpdateDto certificateDto);

}
