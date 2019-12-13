package com.gmail.michzuerch.anouman.backend.data.entity;

import org.junit.Assert;
import org.junit.Test;

public class AddressTest {

	@Test
	public void equalsTest() {
		Address a1 = new Address();
		a1.setCompanyName("TestCompany1");
		a1.setCity("Hamburg");

		Address a2 = new Address();

		a2.setCompanyName("TestCompany2");
		a2.setCity("Budapest");

        // @todo Add equals to EJB
		//Assert.assertNotEquals(a1, a2);

		//o2.setName("name");
		//Assert.assertEquals(o1, o2);
	}
}
