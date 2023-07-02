package ru.clevertec.ecl.dto;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftCertificateReadDto {

    Integer id;

    String name;

    String description;

    BigDecimal price;

    Integer duration;

    LocalDateTime createDate;

    LocalDateTime lastUpdateDate;

    List<TagReadDto> tags;

}
