package ru.clevertec.certificate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.certificate.dto.OrderMakeDto;
import ru.clevertec.certificate.dto.OrderReadDto;
import ru.clevertec.certificate.service.OrderService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
@Validated
public class OrderController {

    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<OrderReadDto>> findAll(Pageable pageable) {
        return new ResponseEntity<>(orderService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderReadDto> findById(@PathVariable @Positive Integer id) {
        return new ResponseEntity<>(orderService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<OrderReadDto> makeOrder(@RequestBody @Validated OrderMakeDto orderMakeDto) {
        return new ResponseEntity<>(orderService.makeOrder(orderMakeDto), HttpStatus.CREATED);
    }

}
