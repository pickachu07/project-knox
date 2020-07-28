package com.srs.knox.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.srs.knox.models.UserCreationStatus;
import com.srs.knox.services.FIUService;
import com.srs.knox.utils.CommandUtil;

@RestController
public class TestController {
	
	@Autowired
	CommandUtil cUtil;
	
	@Autowired
	FIUService fiuService;
	

	@RequestMapping({ "/hi" })
	public String firstPage() {
		UserCreationStatus status =  cUtil.createOwUser("testuser1");
        return status.getMessage();
    }
	
}
