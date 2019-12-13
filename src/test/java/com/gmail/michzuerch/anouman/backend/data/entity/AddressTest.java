package com.gmail.michzuerch.anouman.backend.data.entity;

import org.junit.Assert;
import org.junit.Test;

public class AddressTest {

	@Test
	public void equalsTest() {
		Address a1 = new Address();
		a1.setName("name");
		a1.setPrice(123);

        Address a2 = new Address();
        
		a2.setName("anothername");
		a2.setPrice(123);

		Assert.assertNotEquals(a1, a2);

		//o2.setName("name");
		//Assert.assertEquals(o1, o2);
	}
}
