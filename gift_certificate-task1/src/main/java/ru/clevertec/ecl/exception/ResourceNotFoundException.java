package ru.clevertec.ecl.exception;

import lombok.Getter;
import ru.clevertec.ecl.util.Constant;

@Getter
public class ResourceNotFoundException extends RuntimeException{

    private String fieldName;

    private Object fieldValue;

    private Integer errorCode;

    public ResourceNotFoundException(String fieldName, Object fieldValue, Integer errorCode) {
        super(String.format(Constant.EXCEPTION_MESSAGE, fieldName, fieldValue));
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
        this.errorCode = errorCode;
    }

}
