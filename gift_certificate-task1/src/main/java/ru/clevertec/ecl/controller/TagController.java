package ru.clevertec.ecl.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.TagCreateDto;
import ru.clevertec.ecl.dto.TagReadDto;
import ru.clevertec.ecl.service.TagService;

import javax.validation.constraints.Positive;
import java.util.List;

@RestController
@RequestMapping("/v1/tags")
@RequiredArgsConstructor
@Validated
public class TagController {

    private final TagService tagService;

    @GetMapping
    public ResponseEntity<List<TagReadDto>> findAll(Pageable pageable) {
        return new ResponseEntity<>(tagService.findAll(pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagReadDto> findById(@PathVariable @Positive Integer id) {
        return new ResponseEntity<>(tagService.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TagReadDto> create(@RequestBody @Validated TagCreateDto tagCreateDto) {
        return new ResponseEntity<>(tagService.save(tagCreateDto), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagReadDto> update(@PathVariable @Positive Integer id,
                                             @RequestBody @Validated TagCreateDto tagCreateDto) {
        return new ResponseEntity<>(tagService.update(id, tagCreateDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable @Positive Integer id) {
        tagService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
