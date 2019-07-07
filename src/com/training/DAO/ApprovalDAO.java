package com.training.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Scanner;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.training.InterfaceImpl.InvoiceReader;

public class ApprovalDAO {
	public static Connection connection;

	@SuppressWarnings("resource")
	public void approveInvoice() {
		try {
			System.out.println("Please enter the invoice number to be approved:");
			Scanner scan = new Scanner(System.in);
			String invoiceNum = scan.nextLine();
			try {
				connection = InvoiceDataDAO.connectDB();
				String sqlQuery = "UPDATE INVOICEDATAs SET APPROVAL_STATE='TRUE' WHERE INVOICE_NUM=?";
				PreparedStatement statement = connection.prepareStatement(sqlQuery);
				statement.setString(1, invoiceNum);
				int result = statement.executeUpdate();
				if (result > 0) {
					System.out.println("Invoice approved sucessfully");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void sendApprovalMail(String pop3Host, String mailStoreType, String userName, String password) {
		String to = InvoiceReader.emailFrmAddress;
		String from = userName;
		String pwd = password;
		Properties properties = System.getProperties();
		properties.setProperty("mail.smtp.host", "smtp.gmail.com");
		properties.put("mail.smtp.port", "587");
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.starttls.enable", "true");
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(from, pwd);
			}
		});

		try {
			MimeMessage message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
			message.setSubject("Regarding Status Approval");
			message.setText("Invoice Status Approved.....");

			Transport.send(message);
			System.out.println("Message sent successfully....");

		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}
}