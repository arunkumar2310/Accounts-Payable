package com.training.service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.NoSuchProviderException;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.training.DAO.ApprovalDAO;
import com.training.DAO.InvoiceDataDAO;
import com.training.InterfaceImpl.InvoiceReader;
import com.training.model.InvoiceData;
import com.sun.mail.util.MailConnectException;

@SuppressWarnings("unused")
public class Reader {
	public static String pop3Host = "pop.gmail.com";
	public static String mailStoreType = "pop3";
	public static String userName = "myprojectjdbc@gmail.com";
	public static String password = "jdbc1234";

	public static void main(String[] args) {
		InvoiceReader readerObj = new InvoiceReader();
		int totalMessages = readerObj.receiveEmail(pop3Host, mailStoreType, userName, password);
		if (totalMessages > 0) {
			ApprovalDAO approvalObj = new ApprovalDAO();
			approvalObj.approveInvoice();
			approvalObj.sendApprovalMail(pop3Host, mailStoreType, userName, password);
		} else {
			System.out.println("No new mails");
		}
	}
}
