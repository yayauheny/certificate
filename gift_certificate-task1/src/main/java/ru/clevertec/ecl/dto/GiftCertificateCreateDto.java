package ru.clevertec.ecl.dto;


import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.experimental.FieldDefaults;

import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class GiftCertificateCreateDto {

    String name;

    String description;

    @Positive
    BigDecimal price;

    @Positive
    Integer duration;

    List<TagCreateDto> tags;

}
