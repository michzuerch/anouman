package com.gmail.michael.backend.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.gmail.michael.backend.data.entity.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
