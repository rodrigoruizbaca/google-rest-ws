package com.google.rws.service;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GooglePlayLoginServiceTest {
	@Autowired
	private GooglePlayLoginService service;
	
	/*@Test
	public void login() {
		service.login();
	}*/
	
}
