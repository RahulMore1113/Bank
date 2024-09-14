package com.rahul.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rahul.model.Customer;
import com.rahul.repo.CustomerRepo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final CustomerRepo customerRepo;

	private final PasswordEncoder encoder;

	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody Customer customer) {

		try {

			String hashPwd = encoder.encode(customer.getPwd());
			customer.setPwd(hashPwd);
			Customer savedCustomer = customerRepo.save(customer);

			if (savedCustomer.getId() > 0)
				return ResponseEntity
						.status(HttpStatus.CREATED)
						.body("Given user details are successfully registered!");
			else
				return ResponseEntity
						.status(HttpStatus.BAD_REQUEST)
						.body("User registration failed!");

		} catch (Exception e) {

			return ResponseEntity
					.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body("An exception has occurred : " + e.getMessage());

		}

	}

}
