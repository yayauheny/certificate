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
import ru.clevertec.ecl.dto.TagCreateDto;
import ru.clevertec.ecl.dto.TagReadDto;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.ResourceNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.util.Constant;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TagServiceTest {

    @Mock
    private TagRepository tagRepository;

    @Mock
    private TagMapper tagMapper;

    @InjectMocks
    private TagService tagService;

    private Tag tag;
    private TagReadDto tagReadDto;
    private TagCreateDto tagCreateDto;
    private GiftCertificate certificate;
    private Pageable pageable;
    private Page<Tag> tags;

    @BeforeEach
    void init() {
        tag = getTag();
        tagReadDto = getTagReadDto();
        tagCreateDto = getTagCreateDto();
        certificate = getCertificate();
        pageable = PageRequest.of(Constant.PAGE_NUMBER, Constant.PAGE_SIZE);
        tags = new PageImpl<>(Arrays.asList(tag));
    }

    @Test
    void findAllTest() {
        when(tagRepository.findAll(pageable)).thenReturn(tags);
        when(tagMapper.mapToDto(tag)).thenReturn(tagReadDto);

        assertEquals(1, tagService.findAll(pageable).size());
    }

    @Test
    void findByIdTest() {
        when(tagRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(tag));
        when(tagMapper.mapToDto(tag)).thenReturn(tagReadDto);

        assertEquals(tagReadDto, tagService.findById(Constant.TEST_ID));
    }

    @Test
    void findByIdExceptionTest() {
        when(tagRepository.findById(Constant.TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> tagService.findById(Constant.TEST_ID));
    }

    @Test
    void saveTest() {
        when(tagMapper.mapToEntity(tagCreateDto)).thenReturn(tag);
        when(tagRepository.save(tag)).thenReturn(tag);
        when(tagMapper.mapToDto(tag)).thenReturn(tagReadDto);

        assertEquals(tagReadDto.getId(), tagService.save(tagCreateDto).getId());
    }

    @Test
    void updateTest() {
        when(tagRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(tag));
        when(tagMapper.update(tag, tagCreateDto)).thenReturn(tag);
        when(tagRepository.saveAndFlush(tag)).thenReturn(tag);
        when(tagMapper.mapToDto(tag)).thenReturn(tagReadDto);

        assertEquals(tagReadDto, tagService.update(Constant.TEST_ID, tagCreateDto));

    }

    @Test
    void deleteTest() {
        when(tagRepository.findById(Constant.TEST_ID)).thenReturn(Optional.ofNullable(tag));
        assertTrue(tagService.delete(Constant.TEST_ID));
        verify(tagRepository, times(1)).delete(tag);
    }

    @Test
    void saveTagsTest(){
        when(tagRepository.findByName(Constant.TEST_NAME)).thenReturn(Optional.ofNullable(tag));

        assertEquals(certificate, tagService.saveTags(certificate));
    }

    private Tag getTag() {
        return Tag.builder()
                .id(Constant.TEST_ID)
                .name(Constant.TEST_NAME)
                .build();
    }

    private TagReadDto getTagReadDto() {
        return TagReadDto.builder()
                .id(Constant.TEST_ID)
                .name(Constant.TEST_NAME)
                .build();
    }

    private TagCreateDto getTagCreateDto() {
        return TagCreateDto.builder()
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

}