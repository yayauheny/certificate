package ru.clevertec.ecl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.OrderMakeDto;
import ru.clevertec.ecl.dto.OrderReadDto;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Order;
import ru.clevertec.ecl.entity.User;
import ru.clevertec.ecl.exception.ResourceNotFoundException;
import ru.clevertec.ecl.mapper.OrderMapper;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.repository.OrderRepository;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.util.Constant;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;
    private final GiftCertificateRepository certificateRepository;
    private final OrderMapper orderMapper;


    public List<OrderReadDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable).stream()
                .map(orderMapper::mapToDto)
                .collect(toList());
    }

    public OrderReadDto findById(Integer id) {
        return orderRepository.findById(id)
                .map(orderMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    @Transactional
    public OrderReadDto makeOrder(OrderMakeDto dto) {
        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, dto.getUserId(), Constant.ERROR_CODE));

        GiftCertificate certificate = certificateRepository.findById(dto.getCertificateId())
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, dto.getUserId(), Constant.ERROR_CODE));

        return Optional.of(orderRepository.save(create(user, certificate)))
                .map(orderMapper::mapToDto)
                .get();
    }

    @Transactional
    public Integer setSeq(Integer seq) {
        return orderRepository.setSeq(seq);
    }

    public Integer getSeq() {
        return orderRepository.getSeq();
    }

    private Order create(User user, GiftCertificate certificate) {
        return Order.builder()
                .user(user)
                .certificate(certificate)
                .price(certificate.getPrice())
                .build();
    }

}
