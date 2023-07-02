package ru.clevertec.ecl.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.TagCreateDto;
import ru.clevertec.ecl.dto.TagReadDto;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.exception.ResourceNotFoundException;
import ru.clevertec.ecl.mapper.TagMapper;
import ru.clevertec.ecl.repository.TagRepository;
import ru.clevertec.ecl.util.Constant;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TagService {

    private final TagRepository tagRepository;
    private final TagMapper tagMapper;


    public List<TagReadDto> findAll(Pageable pageable) {
        return tagRepository.findAll(pageable).stream()
                .map(tagMapper::mapToDto)
                .collect(toList());
    }

    public TagReadDto findById(Integer id) {
        return tagRepository.findById(id)
                .map(tagMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    @Transactional
    public TagReadDto save(TagCreateDto tagCreateDto) {
        return Optional.of(tagCreateDto)
                .map(tagMapper::mapToEntity)
                .map(tagRepository::save)
                .map(tagMapper::mapToDto)
                .get();
    }

    @Transactional
    public TagReadDto update(Integer id, TagCreateDto tagCreateDto) {
        return tagRepository.findById(id)
                .map(tag -> tagMapper.update(tag, tagCreateDto))
                .map(tagRepository::saveAndFlush)
                .map(tagMapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));

    }

    @Transactional
    public boolean delete(Integer id) {
        return tagRepository.findById(id)
                .map(card -> {
                    tagRepository.delete(card);
                    return true;
                })
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    @Transactional
    public GiftCertificate saveTags(GiftCertificate certificate) {
        if (certificate.getTags() != null) {
            certificate.getTags().stream()
                    .filter(tag -> !tagRepository.findByName(tag.getName()).isPresent())
                    .forEach(tagRepository::save);

            List<Tag> tagList = certificate.getTags().stream()
                    .map(tag -> tagRepository.findByName(tag.getName()).get())
                    .collect(toList());

            certificate.setTags(tagList);
        }

        return certificate;
    }

}
