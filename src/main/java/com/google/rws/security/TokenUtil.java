package com.google.rws.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.joda.time.DateTime;
import org.springframework.security.crypto.codec.Hex;


public class TokenUtil {
	private static final String MAGIC_KEY = "RodRocks";
	
	private ConcurrentHashMap<String, Map<String, List<String>>> headersMap = new ConcurrentHashMap<String, Map<String, List<String>>>(); 
	
	private static TokenUtil INSTANCE;
	
	public static TokenUtil getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new TokenUtil();
		}
		return INSTANCE;
	}
	
	private TokenUtil() {}
	
	public String createToken(String email) {
		DateTime now = new DateTime();
		now = now.plusDays(1);
		long expires = now.getMillis();
		return expires + ":" + computeSignature(email, expires);
	}
 
	private String computeSignature(String email, long expires) {
		
		StringBuilder signatureBuilder = new StringBuilder();
		signatureBuilder.append(email).append(":");
		signatureBuilder.append(expires).append(":");
		signatureBuilder.append(TokenUtil.MAGIC_KEY);
 
		MessageDigest digest;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			throw new IllegalStateException("No MD5 algorithm available!");
		}
		return new String(Hex.encode(digest.digest(signatureBuilder.toString().getBytes())));
	}
 
	public boolean validateToken(String authToken, String email) {
		String[] parts = authToken.split(":");
		long expires = Long.parseLong(parts[0]);
		String signature = parts[1];
		String signatureToMatch = computeSignature(email, expires);
		return expires >= new DateTime().getMillis() && signature.equals(signatureToMatch);
	}
	
	public void addToken(String token, Map<String, List<String>> headers) {
		headersMap.put(token, headers);
	} 
	
	public Map<String, List<String>> getHeaders(String token) {
		return headersMap.get(token);
	}
}
