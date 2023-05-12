package ru.clevertec.certificate.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.clevertec.certificate.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

}
