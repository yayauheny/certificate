package ru.clevertec.ecl.repository;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import ru.clevertec.ecl.entity.GiftCertificate;

import java.util.List;

public interface GiftCertificateRepository extends JpaRepository<GiftCertificate, Integer>, QuerydslPredicateExecutor<GiftCertificate> {

    @Query("select c from GiftCertificate c join fetch c.tags t where t.name = lower(:name)")
    List<GiftCertificate> findAllByTagName(@Param("name") String tagName, Pageable pageable);

}
