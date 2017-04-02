package com.google.rws.service;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.rws.dto.Song;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class GooglePlayRestTest {
	@Autowired
	private GooglePlayLoginService service;
	
	@Autowired
	private MusicService musicService;
	
	private String email = "rodrigo.ruiz.baca@gmail.com"; 
	private String password = "r0dr1g0rrbR005";
	
	@Test
	public void testGetAllSongs() {
		String token = service.login(email, password);
		List<Song> songs = musicService.getAllSongs(token, email);
		
	}
	
}
