package com.api.ga5000.ecommerce.repositories;

import com.api.ga5000.ecommerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
