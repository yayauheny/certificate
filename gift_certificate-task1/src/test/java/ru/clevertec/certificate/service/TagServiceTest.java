package ru.clevertec.certificate.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.certificate.dto.TagCreateDto;
import ru.clevertec.certificate.dto.TagReadDto;
import ru.clevertec.certificate.entity.Tag;
import ru.clevertec.certificate.exception.ResourceNotFountException;
import ru.clevertec.certificate.mapper.TagMapper;
import ru.clevertec.certificate.repository.TagRepository;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.clevertec.certificate.util.Constant.*;

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
    private Pageable pageable;
    private Page<Tag> tags;

    @BeforeEach
    void init() {
        tag = getTag();
        tagReadDto = getTagReadDto();
        tagCreateDto = getTagCreateDto();
        pageable = PageRequest.of(PAGE_NUMBER, PAGE_SIZE);
        tags = new PageImpl<>(Arrays.asList(tag));
    }

    @Test
    void findAllTest() {
        Mockito.when(tagRepository.findAll(pageable)).thenReturn(tags);
        Mockito.when(tagMapper.mapToDto(tag)).thenReturn(tagReadDto);

        assertEquals(1, tagService.findAll(pageable).size());
    }

    @Test
    void findByIdTest() {
        Mockito.when(tagRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(tag));
        Mockito.when(tagMapper.mapToDto(tag)).thenReturn(tagReadDto);

        assertEquals(tagReadDto, tagService.findById(TEST_ID));
    }

    @Test
    void findByIdExceptionTest() {
        Mockito.when(tagRepository.findById(TEST_ID)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFountException.class, () -> tagService.findById(TEST_ID));
    }

    @Test
    void saveTest() {
        Mockito.when(tagMapper.mapToEntity(tagCreateDto)).thenReturn(tag);
        Mockito.when(tagRepository.save(tag)).thenReturn(tag);
        Mockito.when(tagMapper.mapToDto(tag)).thenReturn(tagReadDto);

        assertEquals(tagReadDto.getId(), tagService.save(tagCreateDto).getId());
    }

    @Test
    void updateTest() {
        Mockito.when(tagRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(tag));
        Mockito.when(tagRepository.saveAndFlush(tag)).thenReturn(tag);
        Mockito.when(tagMapper.mapToDto(tag)).thenReturn(tagReadDto);

        assertEquals(tagReadDto, tagService.update(TEST_ID, tagCreateDto));

    }

    @Test
    void deleteTest() {
        Mockito.when(tagRepository.findById(TEST_ID)).thenReturn(Optional.ofNullable(tag));
        assertTrue(tagService.delete(TEST_ID));
        Mockito.verify(tagRepository, Mockito.times(1)).delete(tag);
    }

    private Tag getTag() {
        return Tag.builder()
                .id(TEST_ID)
                .name(TEST_NAME)
                .build();
    }

    private TagReadDto getTagReadDto() {
        return TagReadDto.builder()
                .id(TEST_ID)
                .name(TEST_NAME)
                .build();
    }

    private TagCreateDto getTagCreateDto() {
        return TagCreateDto.builder()
                .name(TEST_NAME)
                .build();
    }

}