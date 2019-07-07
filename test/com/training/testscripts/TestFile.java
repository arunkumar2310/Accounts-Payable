package com.training.testscripts;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.training.model.InvoiceData;

public class TestFile {
	
	@Test
	public void modelTest(){
		InvoiceData data=new InvoiceData();
		data.setAmount("11235");
		data.setEmailFrmAddress("asd@asd.com");
		assertEquals(data.getAmount(),"11235");
		assertEquals(data.getEmailFrmAddress(),"asd@asd.com");
	}

}
