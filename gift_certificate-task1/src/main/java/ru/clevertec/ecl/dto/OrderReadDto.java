package ru.clevertec.ecl.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class OrderReadDto {

    Integer id;

    UserReadDto user;

    GiftCertificateReadDto certificate;

    BigDecimal price;

    LocalDateTime buyDate;

}
