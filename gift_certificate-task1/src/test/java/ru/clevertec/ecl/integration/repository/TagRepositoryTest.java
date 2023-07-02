package ru.clevertec.ecl.integration.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import ru.clevertec.ecl.entity.Tag;
import ru.clevertec.ecl.integration.TestBase;
import ru.clevertec.ecl.repository.TagRepository;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.clevertec.ecl.util.Constant.TEST_NAME;
import static ru.clevertec.ecl.util.Constant.TEST_NAME_TAG;

@RequiredArgsConstructor
class TagRepositoryTest extends TestBase {

    private final TagRepository tagRepository;

    @Test
    void findByNameTest() {
        Optional<Tag> maybeTag = tagRepository.findByName(TEST_NAME_TAG);

        maybeTag.ifPresent(tag -> assertEquals(TEST_NAME_TAG, tag.getName()));

        assertTrue(maybeTag.isPresent());
    }

    @Test
    void findByNameEmptyTest() {
        Optional<Tag> maybeTag = tagRepository.findByName(TEST_NAME);

        assertFalse(maybeTag.isPresent());
    }

}