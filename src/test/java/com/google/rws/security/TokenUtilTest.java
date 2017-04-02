package com.google.rws.security;

import org.junit.Assert;
import org.junit.Test;



public class TokenUtilTest {
	
	@Test
	public void tokenTest() {
		String token = TokenUtil.getInstance().createToken("rodrigo.ruiz.baca@gmail.com");
		boolean valid = TokenUtil.getInstance().validateToken(token, "rodrigo.ruiz.baca@gmail.com");
		Assert.assertTrue(valid);
	}
	
}
