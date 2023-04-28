package ru.clevertec.certificate.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GiftCertificateCreateDto {

    private String name;

    private String description;

    private BigDecimal price;

    private Integer duration;

    private List<TagCreateDto> tags;

}
