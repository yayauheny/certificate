package ru.clevertec.ecl.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.clevertec.ecl.dto.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.dto.GiftCertificateReadDto;
import ru.clevertec.ecl.dto.GiftCertificateUpdateDto;
import ru.clevertec.ecl.entity.QGiftCertificate;
import ru.clevertec.ecl.exception.ResourceNotFoundException;
import ru.clevertec.ecl.mapper.GiftCertificateMapper;
import ru.clevertec.ecl.predicate.QPredicates;
import ru.clevertec.ecl.repository.GiftCertificateRepository;
import ru.clevertec.ecl.util.Constant;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GiftCertificateService {

    private final GiftCertificateRepository repository;
    private final GiftCertificateMapper mapper;
    private final TagService tagService;


    public List<GiftCertificateReadDto> findAll(GiftCertificateFilter filter, Pageable pageable) {
        return repository.findAll(createPredicate(filter), pageable).stream()
                .map(mapper::mapToDto)
                .collect(toList());
    }

    public GiftCertificateReadDto findById(Integer id) {
        return repository.findById(id)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    public List<GiftCertificateReadDto> findAllByTagsName(String tagName, Pageable pageable) {
        return repository.findAllByTagName(tagName, pageable).stream()
                .map(mapper::mapToDto)
                .collect(toList());
    }

    @Transactional
    public GiftCertificateReadDto save(GiftCertificateCreateDto certificateCreateDto) {
        return Optional.of(certificateCreateDto)
                .map(mapper::mapToEntity)
                .map(tagService::saveTags)
                .map(repository::save)
                .map(mapper::mapToDto)
                .get();
    }

    @Transactional
    public GiftCertificateReadDto update(Integer id, GiftCertificateCreateDto certificateCreateDto) {
        return repository.findById(id)
                .map(certificate -> mapper.update(certificate, certificateCreateDto))
                .map(tagService::saveTags)
                .map(repository::saveAndFlush)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));

    }

    @Transactional
    public GiftCertificateReadDto updatePriceOrDuration(Integer id, GiftCertificateUpdateDto certificateCreateDto){
        return repository.findById(id)
                .map(certificate -> mapper.update(certificate, certificateCreateDto))
                .map(repository::saveAndFlush)
                .map(mapper::mapToDto)
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));

    }

    @Transactional
    public boolean delete(Integer id) {
        return repository.findById(id)
                .map(card -> {
                    repository.delete(card);
                    return true;
                })
                .orElseThrow(() -> new ResourceNotFoundException(Constant.FIELD_NAME_ID, id, Constant.ERROR_CODE));
    }

    private Predicate createPredicate(GiftCertificateFilter filter) {
        return QPredicates.builder()
                .add(filter.getName(), QGiftCertificate.giftCertificate.name::containsIgnoreCase)
                .add(filter.getDescription(), QGiftCertificate.giftCertificate.description::containsIgnoreCase)
                .add(filter.getTags(), QGiftCertificate.giftCertificate.tags.any().name::in)
                .build();
    }

}

