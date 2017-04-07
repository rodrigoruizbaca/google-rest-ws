package com.google.rws.service;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.rws.dto.PlayList;
import com.google.rws.exception.ExpiredOrInvalidTokenException;
import com.google.rws.security.TokenUtil;

import jersey.repackaged.com.google.common.collect.Lists;

@Service
public class MusicService {
	
	private static final Logger log = Logger.getLogger(MusicService.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public List<PlayList> getPlayLists(String token, String email) {
		try {
			log.info("Getting the playlists");
			if (token == null || email == null) {
				throw new IllegalArgumentException("Token and email cant not be epmty");
			}
			boolean isValid = TokenUtil.getInstance().validateToken(token, email);
			if (!isValid) {
				throw new ExpiredOrInvalidTokenException("The token is invalid or its expired, use login again");
			}
			String xt = TokenUtil.getInstance().getCookies(token).get("xt");
			
			String url = "https://play.google.com/music/listen?xt="+xt+"&format=jsarray";
			
			String res = restTemplate.postForObject(url, null, String.class);
			
			Pattern pattern = Pattern.compile("SJ_initialize\\(([^;]*)");
			Matcher m = pattern.matcher(res);
			
			if (m.find()) {
				res = m.group(1);
				res = res.substring(0, res.length() - 1);
			}
			
			pattern = Pattern.compile("\\[\"[^\"]*\",\"[^\"]*\",[0-9]+");
			
			m = pattern.matcher(res);
			
			List<PlayList> playList = Lists.newArrayList();
			
			while (m.find()) {
				String str = m.group();
				String arr [] = str.split(",");
				PlayList pl = new PlayList(arr[0], arr[1]);
				playList.add(pl);
			}
			
			return playList;
			
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	
	
	/*private List<PlayList> createPlayList(List<Object> obj) {
		List<PlayList> result = Lists.newArrayList();
		if (obj.size() > 2) {
			PlayList pl = new PlayList(obj.get(0).toString(), obj.get(1).toString());
			result.add(pl);
			result.addAll(createPlayList(obj.subList(0, 10)));
		}
		return result;
	}*/
	
}
