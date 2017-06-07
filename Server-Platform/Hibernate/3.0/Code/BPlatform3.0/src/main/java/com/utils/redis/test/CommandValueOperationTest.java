package com.utils.redis.test;

import static org.junit.Assert.fail;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.utils.redis.command.CommandValueOperation;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/config/spring/spring-redis.xml")
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CommandValueOperationTest {

	@Autowired
	private CommandValueOperation commandValueOperation;
	
	String key = "vo_key";
	String value = "20170607";
	
	@Test
	public void testAPPEND() {
		long size = commandValueOperation.APPEND(key, value);
		Assert.assertEquals(size, 8);
	}

	@Test
	public void testDECR() {
		fail("Not yet implemented");
	}

	@Test
	public void testDECRBY() {
		fail("Not yet implemented");
	}

	@Test
	public void testDECRBYFloat() {
		fail("Not yet implemented");
	}

	@Test
	public void testGET() {
		String _value = commandValueOperation.GET(key);
		Assert.assertEquals(value, _value);
	}

	@Test
	public void testGETBIT() {
		fail("Not yet implemented");
	}

	@Test
	public void testGETRANGE() {
		fail("Not yet implemented");
	}

	@Test
	public void testGETSET() {
		fail("Not yet implemented");
	}

	@Test
	public void testINCR() {
		fail("Not yet implemented");
	}

	@Test
	public void testINCRBY() {
		fail("Not yet implemented");
	}

	@Test
	public void testINCRBYFLOAT() {
		fail("Not yet implemented");
	}

	@Test
	public void testMGET() {
		fail("Not yet implemented");
	}

	@Test
	public void testMSET() {
		fail("Not yet implemented");
	}

	@Test
	public void testMSETNX() {
		fail("Not yet implemented");
	}

	@Test
	public void testSET() {
		fail("Not yet implemented");
	}

	@Test
	public void testSETBIT() {
		fail("Not yet implemented");
	}

	@Test
	public void testSETEX() {
		fail("Not yet implemented");
	}

	@Test
	public void testSETNX() {
		fail("Not yet implemented");
	}

	@Test
	public void testSETRANGE() {
		fail("Not yet implemented");
	}

	@Test
	public void testSTRLEN() {
		fail("Not yet implemented");
	}

}
