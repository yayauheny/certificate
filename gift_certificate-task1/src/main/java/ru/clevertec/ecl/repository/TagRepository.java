package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.clevertec.ecl.entity.Tag;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {

    Optional<Tag> findByName(String name);

    @Query(value = "select t.id, t.name\n" +
            "from tag t\n" +
            "         join certificate_tag ct on t.id = ct.tag_id\n" +
            "         join orders o on ct.certificate_id = o.certificate_id\n" +
            "where o.user_id = (select o.user_id\n" +
            "                   from orders o\n" +
            "                   group by o.user_id\n" +
            "                   order by sum(o.price) DESC\n" +
            "                   limit 1)\n" +
            "group by t.id\n" +
            "order by count(ct.certificate_id) desc, sum(o.price) desc\n" +
            "limit 1", nativeQuery = true)
    Optional<Tag> findMostUsedTag();

}
