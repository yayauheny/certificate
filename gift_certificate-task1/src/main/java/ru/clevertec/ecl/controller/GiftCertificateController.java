package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.GiftCertificateFilter;
import ru.clevertec.ecl.dto.GiftCertificateReadDto;
import ru.clevertec.ecl.dto.GiftCertificateUpdateDto;
import ru.clevertec.ecl.service.GiftCertificateService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/certificates")
@RequiredArgsConstructor
@Validated
public class GiftCertificateController {

    private final GiftCertificateService service;

    @GetMapping
    public ResponseEntity<List<GiftCertificateReadDto>> findAll(GiftCertificateFilter filter, Pageable pageable) {
        return new ResponseEntity<>(service.findAll(filter, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateReadDto> findById(@PathVariable @Positive Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("/tag/{tagName}")
    public ResponseEntity<List<GiftCertificateReadDto>> findAllByTagsName(@PathVariable String tagName, Pageable pageable) {
        return new ResponseEntity<>(service.findAllByTagsName(tagName, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GiftCertificateReadDto> create(@RequestBody @Validated GiftCertificateCreateDto certificate) {
        return new ResponseEntity<>(service.save(certificate), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateReadDto> update(@PathVariable @Positive Integer id,
                                                         @RequestBody @Validated GiftCertificateCreateDto certificate) {
        return new ResponseEntity<>(service.update(id, certificate), HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<GiftCertificateReadDto> updatePriceOrDuration(@PathVariable @Positive Integer id,
                                                         @RequestBody @Validated GiftCertificateUpdateDto certificate) {
        return new ResponseEntity<>(service.updatePriceOrDuration(id, certificate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}


