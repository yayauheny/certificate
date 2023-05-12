package ru.clevertec.certificate.dto;

import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Value
@Builder
public class OrderReadDto {

    Integer id;

    UserReadDto user;

    GiftCertificateReadDto certificate;

    BigDecimal price;

    LocalDateTime buyDate;

}
