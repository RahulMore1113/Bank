package com.rahul.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

	@GetMapping("/contacts")
	public String saveContactInquiryDetails() {
		return "Contact details saved successfully to the database";
	}

}
