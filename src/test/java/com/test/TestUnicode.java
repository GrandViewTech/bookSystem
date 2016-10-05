package com.test;

import java.io.File;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class TestUnicode {
	
	@Test
	public void testUnicode() throws Exception{
		char c = '\u25aa';
		String s = String.valueOf(c);
		FileUtils.write(new File("uni.txt"), s+" ......ooo", "UTF-8");
		System.out.println(s);
	}

}
