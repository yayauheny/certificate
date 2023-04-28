package ru.clevertec.certificate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import ru.clevertec.certificate.dto.*;
import ru.clevertec.certificate.entity.GiftCertificate;
import ru.clevertec.certificate.entity.Tag;
import ru.clevertec.certificate.exception.ResourceNotFountException;
import ru.clevertec.certificate.mapper.GiftCertificateMapper;
import ru.clevertec.certificate.repository.GiftCertificateRepository;
import ru.clevertec.certificate.repository.TagRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static ru.clevertec.certificate.util.Constant.*;

@ExtendWith(MockitoExtension.class)
class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateRepository repository;

    @Mock
    TagRepository tagRepository;

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
    private ExampleMatcher matcher;

    @BeforeEach
    void init() {
        pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        tag = getTag();
        certificate = getCertificate();
        readDto = getReadDto();
        createDto = getCreateDto();
        tags = new PageImpl<>(Arrays.asList(certificate));
        filter = getFilter();
        matcher = getMatcher();
    }

    @Test
    void findAllTest() {
        Mockito.when(mapper.mapFromFilter(filter)).thenReturn(certificate);
        Mockito.when(repository.findAll(Example.of(certificate, matcher), pageable)).thenReturn(tags);

        Mockito.when(mapper.mapToDto(certificate)).thenReturn(readDto);

        assertEquals(1, service.findAll(filter, pageable).size());
    }

    @Test
    void findByIdTest() {
        Mockito.when(repository.findById(TEST_ID)).thenReturn(Optional.ofNullable(certificate));
        Mockito.when(mapper.mapToDto(certificate)).thenReturn(readDto);

        assertEquals(readDto, service.findById(TEST_ID));
    }

    @Test
    void findAllByTagsNameTest() {
        Mockito.when(repository.findAllByTagsName(TEST_NAME, pageable)).thenReturn(tags);
        Mockito.when(mapper.mapToDto(certificate)).thenReturn(readDto);

        assertEquals(1, service.findAllByTagsName(TEST_NAME, pageable).size());
    }

    @Test
    void findByIdExceptionTest() {
        Mockito.when(repository.findById(TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFountException.class, () -> service.findById(TEST_ID));
    }

    //
    @Test
    void saveTest() {
        Mockito.when(mapper.mapToEntity(createDto)).thenReturn(certificate);
        Mockito.when(tagRepository.findByName(TEST_NAME)).thenReturn(Optional.ofNullable(tag));
        Mockito.when(repository.save(certificate)).thenReturn(certificate);
        Mockito.when(mapper.mapToDto(certificate)).thenReturn(readDto);

        assertEquals(readDto.getId(), service.save(createDto).getId());
    }

    //
    @Test
    void updateTest() {
        Mockito.when(repository.findById(TEST_ID)).thenReturn(Optional.ofNullable(certificate));
        Mockito.when(tagRepository.findByName(TEST_NAME)).thenReturn(Optional.ofNullable(tag));
        Mockito.when(repository.saveAndFlush(certificate)).thenReturn(certificate);
        Mockito.when(mapper.mapToDto(certificate)).thenReturn(readDto);
        Mockito.when(mapper.mapToEntity(createDto)).thenReturn(certificate);

        assertEquals(readDto, service.update(TEST_ID, createDto));
    }

    @Test
    void deleteTest() {
        Mockito.when(repository.findById(TEST_ID)).thenReturn(Optional.ofNullable(certificate));
        assertTrue(service.delete(TEST_ID));
        Mockito.verify(repository, Mockito.times(1)).delete(certificate);
    }

    private Tag getTag() {
        return Tag.builder()
                .id(TEST_ID)
                .name(TEST_NAME)
                .build();
    }

    private GiftCertificate getCertificate() {
        return GiftCertificate.builder()
                .id(TEST_ID)
                .name(TEST_NAME)
                .description(TEST_DESCRIPTION)
                .price(BigDecimal.valueOf(TEST_PRICE))
                .duration(TEST_DURATION)
                .createDate(LocalDateTime.now())
                .tags(Arrays.asList(tag))
                .build();
    }

    private GiftCertificateReadDto getReadDto() {
        return GiftCertificateReadDto.builder()
                .id(TEST_ID)
                .name(TEST_NAME)
                .description(TEST_DESCRIPTION)
                .price(BigDecimal.valueOf(TEST_PRICE))
                .duration(TEST_DURATION)
                .createDate(LocalDateTime.now())
                .tags(Arrays.asList(TagReadDto.builder()
                        .id(TEST_ID)
                        .name(TEST_NAME)
                        .build()))
                .build();
    }

    private GiftCertificateCreateDto getCreateDto() {
        return GiftCertificateCreateDto.builder()
                .name(TEST_NAME)
                .description(TEST_DESCRIPTION)
                .price(BigDecimal.valueOf(TEST_PRICE))
                .duration(TEST_DURATION)
                .tags(Arrays.asList(TagCreateDto.builder()
                        .name(TEST_NAME)
                        .build()))
                .build();
    }

    private GiftCertificateFilter getFilter() {
        return GiftCertificateFilter.builder()
                .name(FILTER_NAME)
                .description(FILTER_DESCRIPTION)
                .build();
    }

    private ExampleMatcher getMatcher() {
        return ExampleMatcher.matching()
                .withMatcher(FIELD_NAME_NAME, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher(FIELD_NAME_DESCRIPTION, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
    }

}