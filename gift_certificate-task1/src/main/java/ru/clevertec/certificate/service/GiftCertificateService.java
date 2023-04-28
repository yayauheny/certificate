package ru.clevertec.certificate.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.certificate.dto.*;
import ru.clevertec.certificate.entity.GiftCertificate;
import ru.clevertec.certificate.entity.Tag;
import ru.clevertec.certificate.exception.ResourceNotFountException;
import ru.clevertec.certificate.mapper.GiftCertificateMapper;
import ru.clevertec.certificate.repository.GiftCertificateRepository;
import ru.clevertec.certificate.repository.TagRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static ru.clevertec.certificate.util.Constant.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftCertificateService {

    private final GiftCertificateRepository repository;
    private final GiftCertificateMapper mapper;

    private final TagRepository tagRepository;


    public List<GiftCertificateReadDto> findAll(GiftCertificateFilter filter, Pageable pageable) {
        GiftCertificate certificate = mapper.mapFromFilter(filter);
        ExampleMatcher matcher = ExampleMatcher.matching()
                .withMatcher(FIELD_NAME_NAME, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
                .withMatcher(FIELD_NAME_DESCRIPTION, ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
        return repository.findAll(Example.of(certificate, matcher), pageable).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());

    }

    public GiftCertificateReadDto findById(Integer id) {
        return repository.findById(id)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFountException(FIELD_NAME_ID, id, ERROR_CODE));
    }

    public List<GiftCertificateReadDto> findAllByTagsName(String name, Pageable pageable) {
        return repository.findAllByTagsName(name, pageable).stream()
                .map(mapper::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public GiftCertificateReadDto save(GiftCertificateCreateDto certificateCreateDto) {
        return Optional.of(certificateCreateDto)
                .map(mapper::mapToEntity)
                .map(this::saveTags)
                .map(repository::save)
                .map(mapper::mapToDto)
                .get();
    }

    @Transactional
    public GiftCertificateReadDto update(Integer id, GiftCertificateCreateDto certificateCreateDto) {
        return repository.findById(id)
                .map(certificate -> copy(certificate, certificateCreateDto))
                .map(this::saveTags)
                .map(repository::saveAndFlush)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFountException(FIELD_NAME_ID, id, ERROR_CODE));

    }

    @Transactional
    public boolean delete(Integer id) {
        return repository.findById(id)
                .map(card -> {
                    repository.delete(card);
                    return true;
                })
                .orElseThrow(() -> new ResourceNotFountException(FIELD_NAME_ID, id, ERROR_CODE));
    }

    private GiftCertificate copy(GiftCertificate certificate, GiftCertificateCreateDto certificateCreateDto) {
        if (certificateCreateDto.getName() != null) {
            certificate.setName(certificateCreateDto.getName());
        }

        if (certificateCreateDto.getDescription() != null) {
            certificate.setDescription(certificateCreateDto.getDescription());
        }

        if (certificateCreateDto.getPrice() != null) {
            certificate.setPrice(certificateCreateDto.getPrice());
        }

        if (certificateCreateDto.getDuration() != null) {
            certificate.setDuration(certificateCreateDto.getDuration());
        }

        if (certificateCreateDto.getTags() != null) {
            GiftCertificate giftCertificate = mapper.mapToEntity(certificateCreateDto);
            certificate.setTags(giftCertificate.getTags());
        }
        return certificate;
    }

    private GiftCertificate saveTags(GiftCertificate certificate) {
        certificate.getTags().stream()
                .filter(tag -> !tagRepository.findByName(tag.getName()).isPresent())
                .forEach(tagRepository::save);

        List<Tag> tagList = certificate.getTags().stream()
                .map(tag -> tagRepository.findByName(tag.getName()).get())
                .collect(Collectors.toList());

        certificate.setTags(tagList);

        return certificate;
    }

}

