package com.cat.battle.service;

import org.apache.dubbo.config.annotation.Service;

import com.cat.api.dto.Tester;
import com.cat.api.service.ITestService;

@Service
public class TestService implements ITestService{
	
	public TestService() {
		System.out.println("========");
	}
	
	public String test(Tester tester) {
		return "Hello, ".concat(tester.getName());
	}

}
