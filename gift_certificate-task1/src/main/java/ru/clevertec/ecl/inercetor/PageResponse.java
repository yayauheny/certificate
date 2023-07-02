package ru.clevertec.ecl.inercetor;

import lombok.Value;
import ru.clevertec.ecl.dto.OrderReadDto;
import ru.clevertec.ecl.util.Constant;

import java.util.Comparator;

@Value
public class PageResponse {

    int size;

    int page;

    Comparator<OrderReadDto> comparing;

    public static PageResponse of(String size, String page, String sort){
        int pageSize = size != null ? Integer.parseInt(size) : Constant.PAGE_SIZE;
        int pageNumber = page != null ? Integer.parseInt(page) * pageSize : Constant.PAGE_NUMBER;
        Comparator<OrderReadDto> comparator = Constant.FIELD_NAME_PRICE.equals(sort) ?
                Comparator.comparing(OrderReadDto::getPrice) : Comparator.comparing(OrderReadDto::getId);

        return new PageResponse(pageSize, pageNumber, comparator);
    }

}
