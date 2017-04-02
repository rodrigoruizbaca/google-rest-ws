package com.google.rws.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.google.rws.exception.LoginException;
import com.google.rws.security.TokenUtil;

@Service
public class GooglePlayLoginService {
	@Autowired
	private RestTemplate restTemplate;
	

	private static final Logger log = Logger.getLogger(GooglePlayLoginService.class);
	
	/**
	 * Gets the form element on the page. If there are more than one form or none, will throw an exception at runtime.
	 * @param url The url to parse
	 * @return The Form element
	 */
	protected Element getForm(String html) {
		Document doc = Jsoup.parse(html);
		return getForm(doc);
	}
	
	/**
	 * Gets the form element on the page. If there are more than one form or none, will throw an exception at runtime.
	 * @param url The url to parse
	 * @return The Form element
	 */
	protected Element getForm(Document doc) {
		log.info("Trying to access the ServiceLoginAuth page to obtain the authentication form");
		Elements forms = doc.getElementsByTag("form");
		if (forms.size() != 1) {
			throw new LoginException("Email or password incorrect");
		}
		return forms.get(0);
	}
	
	
	protected MultiValueMap<String, String> getFormDataToSubmit(Element form) {
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		log.info("Getting the form data");
		Elements childs = form.getElementsByTag("input");
		for (Element e: childs) {	
			String name = e.attr("name");
			String value = e.attr("value");
			map.add(name, value);
		}
		return map;
	}
	
	
	public String login(String email, String password) {
		String htmlAuth = restTemplate.getForObject("https://accounts.google.com/ServiceLoginAuth?service=sj&continue=https://play.google.com/music/listen", String.class);
		Element form = getForm(htmlAuth);
		form.select("#Email").get(0).attr("value", email);
		MultiValueMap<String, String> map = getFormDataToSubmit(form);
		if (form.select("#Passwd").size() == 0) {
			String htmlProfile = restTemplate.postForObject("https://accounts.google.com/AccountLoginInfo", map, String.class);
			form = getForm(htmlProfile);
		}
		form.select("#Passwd").get(0).attr("value", password);
		map = getFormDataToSubmit(form);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
		HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
		
		HttpEntity<String> response = restTemplate.exchange("https://accounts.google.com/ServiceLoginAuth", 
				HttpMethod.POST, request, String.class);
		HttpHeaders responseHeaders = response.getHeaders();
		
		List<String> values = responseHeaders.get("Set-Cookie");
		if (values == null) {
			log.error("Email or password incorrect");
			throw new LoginException("Email or password incorrect");
		}
		List<String> sidCookie = values.stream().filter(cookie -> cookie.startsWith("SID")).collect(Collectors.toList());
		
		if (sidCookie == null || sidCookie.isEmpty()) {
			log.error("Email or password incorrect");
			throw new LoginException("Email or password incorrect");
		}
		
		/*Map<String, String> mapcookies = new HashMap<String, String>();	
		String cookie = sidCookie.get(0);
		String[] sidArr = cookie.split(";");
		for (String s: sidArr) {
			String[] arr = s.split("=");
			mapcookies.put(arr[0], arr[1]);
		}*/
		
		//String expires = mapcookies.get("Expires");
		
		Map<String, List<String>> mapcookies = new HashMap<String, List<String>>();
		
		for (String k: responseHeaders.keySet()) {
			mapcookies.put(k, responseHeaders.get(k));
		}
		
		String token = TokenUtil.getInstance().createToken(email);
		
		TokenUtil.getInstance().addToken(token, mapcookies);		
		
		return token;
	}
	
	
}