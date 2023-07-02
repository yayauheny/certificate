package ru.clevertec.ecl.integration.repository;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import ru.clevertec.ecl.entity.GiftCertificate;
import ru.clevertec.ecl.integration.TestBase;
import ru.clevertec.ecl.repository.GiftCertificateRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.clevertec.ecl.util.Constant.*;

@RequiredArgsConstructor
class GiftCertificateRepositoryTest extends TestBase {

    private final GiftCertificateRepository repository;

    @Test
    void findAllByTagsNameTest() {
        List<GiftCertificate> sport = repository.findAllByTagName(TEST_NAME_TAG, PageRequest.of(PAGE_NUMBER, PAGE_SIZE));
        assertEquals(5, sport.size());
    }

}