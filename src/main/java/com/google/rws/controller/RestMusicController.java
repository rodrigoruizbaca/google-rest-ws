package com.google.rws.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.rws.dto.Song;
import com.google.rws.service.MusicService;

@CrossOrigin
@RequestMapping("music")
@RestController
public class RestMusicController {
	
	@Autowired
	private MusicService musicService;
	
	@RequestMapping(value = "tracks/{email}/{token}", method = RequestMethod.GET)
	public List<Song> getAllsongs(@PathVariable("email") String email, @PathVariable("token") String token) {
		return musicService.getAllSongs(token, email);
	}
}
