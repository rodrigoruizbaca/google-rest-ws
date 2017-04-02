package com.google.rws.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.rws.dto.Song;
import com.google.rws.exception.ExpiredOrInvalidTokenException;
import com.google.rws.security.TokenUtil;

@Service
public class MusicService {
	
	private static final Logger log = Logger.getLogger(MusicService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	public List<Song> getAllSongs(String token, String email) {
		try {
			if (token == null || email == null) {
				throw new IllegalArgumentException("Token and email cant not be epmty");
			}
			boolean isValid = TokenUtil.getInstance().validateToken(token, email);
			if (!isValid) {
				throw new ExpiredOrInvalidTokenException("The token is invalid or its expired, use login again");
			}
			log.info("Trying to get all the songs");
			HttpHeaders headers = new HttpHeaders();
			headers.putAll(TokenUtil.getInstance().getHeaders(token));
			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			
			HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(new LinkedMultiValueMap<String, String>(), headers);
			
			HttpEntity<String> response = restTemplate.exchange("https://music.google.com/music/exportids", HttpMethod.POST, request, String.class);
			String body = response.getBody();
			ObjectMapper mapper = new ObjectMapper();
			List<Song> list = mapper.readValue(body, new TypeReference<List<Song>>() {});
			return list;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
	}
}
