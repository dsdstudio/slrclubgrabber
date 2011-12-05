package net.dsdstudio.slrclubgrabber.test;

import java.io.IOException;

import net.dsdstudio.slrclubgrabber.main.Main;

import org.junit.Before;
import org.junit.Test;

public class GrabTest {

	@Before
	public void setUp() {
	}

	/**
	 * @throws IOException
	 * 
	 */
	@Test
	public void test() throws IOException {
		String[] args = { "userid", "password",
				"/directory/", "조공" };
		Main.main(args);
	}
}
