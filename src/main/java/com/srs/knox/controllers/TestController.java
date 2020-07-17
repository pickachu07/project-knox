package com.srs.knox.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

	@RequestMapping({ "/hi" })
	public String firstPage() {
		return "Hello says Knox!";
	}
}
