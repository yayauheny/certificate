package ru.clevertec.certificate.exception;

import lombok.Getter;

import static ru.clevertec.certificate.util.Constant.EXCEPTION_MESSAGE;

@Getter
public class ResourceNotFountException extends RuntimeException{

    private String fieldName;

    private Object fieldValue;

    private Integer errorCode;

    public ResourceNotFountException(String fieldName, Object fieldValue, Integer errorCode) {
        super(String.format(EXCEPTION_MESSAGE, fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.errorCode = errorCode;
    }

}
