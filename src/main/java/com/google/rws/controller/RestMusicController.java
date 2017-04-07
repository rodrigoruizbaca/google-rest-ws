package com.google.rws.controller;

import java.util.List;

import javax.ws.rs.QueryParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.google.rws.dto.PlayList;
import com.google.rws.service.MusicService;

@CrossOrigin
@RequestMapping("music")
@RestController
public class RestMusicController {
	
	@Autowired
	private MusicService musicService;
	
	@RequestMapping(value = "/tracks/{token}/", method = RequestMethod.GET)
	public List<PlayList> getPlayLists(@PathVariable("token") String token, @QueryParam("email") String email) {
		return musicService.getPlayLists(token, email);
	}
}
