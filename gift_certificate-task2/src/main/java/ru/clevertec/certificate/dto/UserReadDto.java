package ru.clevertec.certificate.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserReadDto {

    Integer id;

    String username;

    String firstname;

    String lastname;

    String tel;

    String address;

}
