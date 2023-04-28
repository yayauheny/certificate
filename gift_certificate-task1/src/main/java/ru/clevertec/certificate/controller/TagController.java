package ru.clevertec.certificate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.certificate.dto.TagCreateDto;
import ru.clevertec.certificate.dto.TagReadDto;
import ru.clevertec.certificate.service.TagService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/tags")
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagReadDto>> findAll(Pageable pageable) {
        return new ResponseEntity<>(tagService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagReadDto> findById(@PathVariable Integer id) {
        return new ResponseEntity<>(tagService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TagReadDto> create(@RequestBody TagCreateDto tagCreateDto) {
        return new ResponseEntity<>(tagService.save(tagCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagReadDto> update(@PathVariable Integer id,
                                             @RequestBody TagCreateDto tagCreateDto) {
        return new ResponseEntity<>(tagService.update(id, tagCreateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
