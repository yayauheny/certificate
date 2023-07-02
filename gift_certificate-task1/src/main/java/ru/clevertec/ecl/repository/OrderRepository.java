package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.clevertec.ecl.entity.Order;;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    @Query(value = "SELECT setval('orders_id_seq', :seq) ", nativeQuery = true)
    Integer setSeq(@Param("seq") Integer seq);

    @Query(value = "SELECT last_value FROM orders_id_seq", nativeQuery = true)
    Integer getSeq();

}
