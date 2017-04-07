package com.google.rws.util;

import org.junit.Assert;
import org.junit.Test;



public class UtilTest {
	
	@Test
	public void testAsString() {
		JsonUtil u = new JsonUtil();
		String subs = u.asString("c11a82c6-2dc4-4a67-a93e-88a0493834de\", \"Uno noche en madrid\", [\"https://lh3.ggpht.com/jNLH6Y8UPyBnyR4sGept8DTp5A0TBB3INLm8uWpHnQJuxyu_dZPbBmm5g4lerASQSI4H4L0\"]]]]");
		Assert.assertEquals("c11a82c6-2dc4-4a67-a93e-88a0493834de", subs);
	}
	
	@Test
	public void testAsOther() {
		JsonUtil u = new JsonUtil();
		String subs = u.asOther("1235,3446]]");
		Assert.assertEquals("1235", subs);
		
		subs = u.asOther("3446]]");
		Assert.assertEquals("3446", subs);
	}
	
}
