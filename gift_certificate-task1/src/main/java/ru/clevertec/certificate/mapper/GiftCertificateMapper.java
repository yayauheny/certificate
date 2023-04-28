package ru.clevertec.certificate.mapper;

import org.mapstruct.Mapper;
import ru.clevertec.certificate.dto.GiftCertificateCreateDto;
import ru.clevertec.certificate.dto.GiftCertificateFilter;
import ru.clevertec.certificate.dto.GiftCertificateReadDto;
import ru.clevertec.certificate.entity.GiftCertificate;

@Mapper(componentModel = "spring")
public interface GiftCertificateMapper {

    GiftCertificateReadDto mapToDto(GiftCertificate certificate);

    GiftCertificate mapToEntity(GiftCertificateCreateDto certificate);

    GiftCertificate mapFromFilter(GiftCertificateFilter filter);

}
