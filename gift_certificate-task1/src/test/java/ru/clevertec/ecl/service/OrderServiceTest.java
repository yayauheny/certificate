package ru.clevertec.ecl.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.*;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.exception.ResourceNotFoundException;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.util.Constant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GiftCertificateRepository certificateRepository;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderService orderService;

    private Pageable pageable;
    private User user;
    private GiftCertificate certificate;
    private Page<Order> orders;
    private OrderReadDto orderReadDto;
    private Order order;
    private Order orderCreate;

    @BeforeEach
    void init() {
        pageable = PageRequest.of(Constant.PAGE_NUMBER, Constant.PAGE_SIZE);
        user = getUser();
        certificate = getCertificate();
        order = getOrder();
        orderCreate = getOrderCreate();
        orderReadDto = getOrderReadDto();
        orders = new PageImpl<>(Arrays.asList(order));
    }

    @Test
    void findByAllTest() {
        when(orderRepository.findAll(pageable)).thenReturn(orders);
        when(orderMapper.mapToDto(order)).thenReturn(orderReadDto);

        assertEquals(1, orderService.findAll(pageable).size());
    }

    @Test
    void findByIdTest() {
        when(orderRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(order));
        when(orderMapper.mapToDto(order)).thenReturn(orderReadDto);

        assertEquals(orderReadDto, orderService.findById(Constant.TEST_ID));
    }

    @Test
    void findByIdExceptionTest() {
        when(orderRepository.findById(Constant.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> orderService.findById(Constant.TEST_ID));
    }

    @Test
    void makeOrderTest() {
        when(userRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(user));
        when(certificateRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(certificate));
        when(orderRepository.save(orderCreate)).thenReturn(order);
        when(orderMapper.mapToDto(order)).thenReturn(orderReadDto);

        assertEquals(orderReadDto, orderService.makeOrder(getOrderMakeDto()));

    }

    private User getUser() {
        return User.builder()
                .id(Constant.TEST_ID)
                .username(Constant.TEST_USERNAME)
                .firstname(Constant.TEST_NAME)
                .lastname(Constant.TEST_NAME)
                .tel(Constant.TEST_TEL)
                .address(Constant.TEST_ADDRESS)
                .build();
    }

    private UserReadDto getUserReadDto() {
        return UserReadDto.builder()
                .id(Constant.TEST_ID)
                .username(Constant.TEST_USERNAME)
                .firstname(Constant.TEST_NAME)
                .lastname(Constant.TEST_NAME)
                .tel(Constant.TEST_TEL)
                .address(Constant.TEST_ADDRESS)
                .build();
    }

    private GiftCertificate getCertificate() {
        return GiftCertificate.builder()
                .id(Constant.TEST_ID)
                .name(Constant.TEST_NAME)
                .description(Constant.TEST_DESCRIPTION)
                .price(BigDecimal.valueOf(Constant.TEST_PRICE))
                .duration(Constant.TEST_DURATION)
                .createDate(LocalDateTime.now())
                .tags(Arrays.asList(Tag.builder()
                        .id(Constant.TEST_ID)
                        .name(Constant.TEST_NAME)
                        .build()))
                .build();
    }

    private GiftCertificateReadDto getReadDto() {
        return GiftCertificateReadDto.builder()
                .id(Constant.TEST_ID)
                .name(Constant.TEST_NAME)
                .description(Constant.TEST_DESCRIPTION)
                .price(BigDecimal.valueOf(Constant.TEST_PRICE))
                .duration(Constant.TEST_DURATION)
                .createDate(LocalDateTime.now())
                .tags(Arrays.asList(TagReadDto.builder()
                        .id(Constant.TEST_ID)
                        .name(Constant.TEST_NAME)
                        .build()))
                .build();
    }

    private Order getOrder() {
        return Order.builder()
                .id(Constant.TEST_ID)
                .user(user)
                .certificate(certificate)
                .price(BigDecimal.valueOf(Constant.TEST_PRICE))
                .buyDate(LocalDateTime.now())
                .build();
    }

    private OrderReadDto getOrderReadDto() {
        return OrderReadDto.builder()
                .id(Constant.TEST_ID)
                .user(getUserReadDto())
                .certificate(getReadDto())
                .price(BigDecimal.valueOf(Constant.TEST_PRICE))
                .buyDate(LocalDateTime.now())
                .build();
    }

    private Order getOrderCreate() {
        return Order.builder()
                .user(user)
                .certificate(certificate)
                .price(BigDecimal.valueOf(Constant.TEST_PRICE))
                .build();
    }

    private OrderMakeDto getOrderMakeDto() {
        return OrderMakeDto.builder()
                .userId(Constant.TEST_ID)
                .certificateId(Constant.TEST_ID)
                .build();
    }

}