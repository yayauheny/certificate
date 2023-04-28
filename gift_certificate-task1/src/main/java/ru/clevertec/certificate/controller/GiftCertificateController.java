package ru.clevertec.certificate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.certificate.dto.GiftCertificateCreateDto;
import ru.clevertec.certificate.dto.GiftCertificateFilter;
import ru.clevertec.certificate.dto.GiftCertificateReadDto;
import ru.clevertec.certificate.service.GiftCertificateService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/certificates")
@RequiredArgsConstructor
public class GiftCertificateController {

    private final GiftCertificateService service;

    @GetMapping
    public ResponseEntity<List<GiftCertificateReadDto>> findAll(GiftCertificateFilter filter, Pageable pageable) {
        return new ResponseEntity<>(service.findAll(filter, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateReadDto> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(service.findById(id), HttpStatus.OK);
    }

    @GetMapping("/tag")
    public ResponseEntity<List<GiftCertificateReadDto>> findAllByTagsName(String name, Pageable pageable) {
        return new ResponseEntity<>(service.findAllByTagsName(name, pageable), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<GiftCertificateReadDto> create(@RequestBody GiftCertificateCreateDto certificate) {
        return new ResponseEntity<>(service.save(certificate), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateReadDto> update(@PathVariable Integer id,
                                                         @RequestBody GiftCertificateCreateDto certificate) {
        return new ResponseEntity<>(service.update(id, certificate), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
