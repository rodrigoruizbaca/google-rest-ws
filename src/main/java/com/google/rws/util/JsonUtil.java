package com.google.rws.util;

import java.util.AbstractList;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tomcat.util.digester.ArrayStack;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.google.api.client.util.Lists;

@Component
public class JsonUtil {
	
	private static final Logger log = Logger.getLogger(JsonUtil.class);
	
	public List<Object> convertJsonArray(String jsonArray) {
		log.info("Converting an jarray to a list of objects");
		ArrayStack<List<Object>> queue = new ArrayStack<List<Object>>();
		List<Object> result = Lists.newArrayList();
		if (jsonArray.length() > 0) {					
			process(jsonArray, queue, result);				
		}
		return result;
	}
	
	private void process(String str, ArrayStack<List<Object>> queue, List<Object> main) {
		if (!str.isEmpty()) {
			if (str.charAt(0) == '[') {
				List<Object> current = Lists.newArrayList();				
				if (!queue.isEmpty()) {
					queue.peek().add(current);
				} else {
					main.add(current);
				}				
				queue.push(current);
				str = str.substring(1);
			} else if (str.charAt(0) == ']') {
				queue.pop();
				str = str.substring(1);
			} else {				
				String subs = getSubstring(str);				
				str = str.substring(1);
				if (!subs.isEmpty() && !subs.startsWith(",") && !subs.equals("\n")) {					
					queue.peek().add(subs);
				}
				if (str.startsWith("\"")) {
					str.substring(1);
				}
				str = StringUtils.delete(str, subs);
			}
			process(str, queue, main);
		} 
	}
	
	private String getSubstring(String str) {
		if (str.startsWith("\"")) {
			return asString(str.substring(1));
		} else {
			return asOther(str);
		}
	}
	
	protected String asOther(String str) {
		StringBuilder substr = new StringBuilder();;
		if (!str.isEmpty()) {
			if (str.charAt(0) != ']' && str.charAt(0) != ',') {
				substr.append(str.charAt(0));
				substr.append(asOther(str.substring(1)));
				return substr.toString();
			} else {
				return substr.toString();
			}
		} else {
			return substr.toString();
		}
	}
	
	protected String asString(String str) {
		StringBuilder substr = new StringBuilder();
		if (!str.isEmpty()) {
			if (str.charAt(0) != '"') {
				substr.append(str.charAt(0));
				substr.append(asString(str.substring(1)));
				return substr.toString();
			} else {
				return substr.toString();
			}
		} else {
			return substr.toString();
		}
	}
	
	public List<Character> asList(final String string) {
	    return new AbstractList<Character>() {
	       public int size() { return string.length(); }
	       public Character get(int index) { return string.charAt(index); }
	    };
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> getFinalList(List<Object> lst) {
		List<Object> result = Lists.newArrayList();
		for (Object obj: lst) {
			if (obj instanceof String) {
				return lst;
			} else {
				result = getFinalList((List<Object>)obj);
			}
		}
		return result;
	}
}
