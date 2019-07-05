package com.training.model;

public class InvoiceData {
	private String invoiceDate = "";
	private String customerPO = "";
	private String amount = "";
	private String invoiceNum = "";
	private String emailFrmAddress;
	private String soldToAddress = "";
	private String shipToAddress = "";
	private String remitToAddress = "";
	private String state = "";

	public String getInvoiceDate() {
		return invoiceDate;
	}

	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}

	public String getCustomerPO() {
		return this.customerPO;
	}

	public void setCustomerPO(String customerPO) {
		this.customerPO = customerPO;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getInvoiceNum() {
		return invoiceNum;
	}

	public void setInvoiceNum(String invoiceNum) {
		this.invoiceNum = invoiceNum;
	}

	public String getEmailFrmAddress() {
		return emailFrmAddress;
	}

	public void setEmailFrmAddress(String emailFrmAddress2) {
		this.emailFrmAddress = emailFrmAddress2;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getSoldToAddress() {
		return soldToAddress;
	}

	public void setSoldToAddress(String soldToAddress) {
		this.soldToAddress = soldToAddress;
	}

	public String getShipToAddress() {
		return shipToAddress;
	}

	public void setShipToAddress(String shipToAddress) {
		this.shipToAddress = shipToAddress;
	}

	public String getRemitToAddress() {
		return remitToAddress;
	}

	public void setRemitToAddress(String remitToAddress) {
		this.remitToAddress = remitToAddress;
	}

}
