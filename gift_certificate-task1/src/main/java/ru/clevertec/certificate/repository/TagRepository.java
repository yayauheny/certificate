package ru.clevertec.certificate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.certificate.entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    public Optional<Tag> findByName(String name);

}
