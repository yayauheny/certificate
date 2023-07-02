package ru.clevertec.ecl.service;

import com.querydsl.core.types.Predicate;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import ru.clevertec.ecl.dto.*;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.QGiftCertificate;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.ResourceNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.predicate.QPredicates;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.util.Constant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateRepository repository;

    @Mock
    private TagService tagService;

    @Mock
    private GiftCertificateMapper mapper;

    @InjectMocks
    private GiftCertificateService service;

    private Tag tag;
    private Pageable pageable;
    private Page<GiftCertificate> tags;
    private GiftCertificate certificate;
    private GiftCertificateReadDto readDto;
    private GiftCertificateCreateDto createDto;
    private GiftCertificateFilter filter;
    private GiftCertificateUpdateDto updateDto;
    private Predicate predicate;

    @BeforeEach
    void init() {
        pageable = PageRequest.of(Constant.PAGE_NUMBER, Constant.PAGE_SIZE);
        tag = getTag();
        certificate = getCertificate();
        readDto = getReadDto();
        updateDto = getUpdateDto();
        createDto = getCreateDto();
        tags = new PageImpl<>(Arrays.asList(certificate));
        filter = getFilter();
        predicate = getPredicate();
    }

    @Test
    void findAllTest() {
        when(repository.findAll(predicate, pageable)).thenReturn(tags);
        when(mapper.mapToDto(certificate)).thenReturn(readDto);

        assertEquals(1, service.findAll(filter, pageable).size());
    }

    @Test
    void findByIdTest() {
        when(repository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(certificate));
        when(mapper.mapToDto(certificate)).thenReturn(readDto);

        assertEquals(readDto, service.findById(Constant.TEST_ID));
    }

    @Test
    void findAllByTagsNameTest() {
        when(repository.findAllByTagName(Constant.TEST_NAME, pageable)).thenReturn(tags.getContent());
        when(mapper.mapToDto(certificate)).thenReturn(readDto);

        assertEquals(1, service.findAllByTagsName(Constant.TEST_NAME, pageable).size());
    }

    @Test
    void findByIdExceptionTest() {
        when(repository.findById(Constant.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById(Constant.TEST_ID));
    }

    //
    @Test
    void saveTest() {
        when(mapper.mapToEntity(createDto)).thenReturn(certificate);
        when(tagService.saveTags(certificate)).thenReturn(certificate);
        when(repository.save(certificate)).thenReturn(certificate);
        when(mapper.mapToDto(certificate)).thenReturn(readDto);

        assertEquals(readDto.getId(), service.save(createDto).getId());
    }

    //
    @Test
    void updateTest() {
        when(repository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(certificate));
        when(mapper.update(certificate, createDto)).thenReturn(certificate);
        when(tagService.saveTags(certificate)).thenReturn(certificate);
        when(repository.saveAndFlush(certificate)).thenReturn(certificate);
        when(mapper.mapToDto(certificate)).thenReturn(readDto);

        assertEquals(readDto, service.update(Constant.TEST_ID, createDto));
    }

    @Test
    void updatePriceOrDurationTest() {
        when(repository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(certificate));
        when(mapper.update(certificate, updateDto)).thenReturn(certificate);
        when(repository.saveAndFlush(certificate)).thenReturn(certificate);
        when(mapper.mapToDto(certificate)).thenReturn(readDto);

        assertEquals(readDto, service.updatePriceOrDuration(Constant.TEST_ID, updateDto));
    }

    @Test
    void deleteTest() {
        when(repository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(certificate));
        assertTrue(service.delete(Constant.TEST_ID));
        Mockito.verify(repository, Mockito.times(1)).delete(certificate);
    }

    private Tag getTag() {
        return Tag.builder()
                .id(Constant.TEST_ID)
                .name(Constant.TEST_NAME)
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
                .tags(Arrays.asList(tag))
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

    private GiftCertificateCreateDto getCreateDto() {
        return GiftCertificateCreateDto.builder()
                .name(Constant.TEST_NAME)
                .description(Constant.TEST_DESCRIPTION)
                .price(BigDecimal.valueOf(Constant.TEST_PRICE))
                .duration(Constant.TEST_DURATION)
                .tags(Arrays.asList(TagCreateDto.builder()
                        .name(Constant.TEST_NAME)
                        .build()))
                .build();
    }

    private GiftCertificateFilter getFilter() {
        return GiftCertificateFilter.builder()
                .name(Constant.FILTER_NAME)
                .description(Constant.FILTER_DESCRIPTION)
                .build();
    }

    private GiftCertificateUpdateDto getUpdateDto() {
        return GiftCertificateUpdateDto.builder()
                .price(BigDecimal.valueOf(Constant.TEST_PRICE))
                .duration(Constant.TEST_DURATION)
                .build();
    }

    private Predicate getPredicate() {
        return QPredicates.builder()
                .add(filter.getName(), QGiftCertificate.giftCertificate.name::containsIgnoreCase)
                .add(filter.getDescription(), QGiftCertificate.giftCertificate.description::containsIgnoreCase)
                .build();
    }

}