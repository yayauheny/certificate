package ru.clevertec.certificate.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class GiftCertificateFilter {

    String name;

    String description;

}
