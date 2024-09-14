package com.rahul.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.rahul.model.Customer;

@Repository
public interface CustomerRepo extends CrudRepository<Customer, Integer> {

	Optional<Customer> findByEmail(String email);

}
