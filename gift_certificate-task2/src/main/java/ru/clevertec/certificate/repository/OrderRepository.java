package ru.clevertec.certificate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.certificate.entity.Order;;

public interface OrderRepository extends JpaRepository<Order, Integer> {

}
