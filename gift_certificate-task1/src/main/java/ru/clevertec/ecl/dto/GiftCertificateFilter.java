package ru.clevertec.ecl.dto;

import lombok.Builder;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class GiftCertificateFilter {

    String name;

    String description;

    List<String> tags;

}
