package com.trainnig.InterfaceImpl;

import java.io.File;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

import javax.mail.BodyPart;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Part;
import javax.mail.Session;
import javax.mail.Store;

import com.sun.mail.util.MailConnectException;
import com.training.Interface.InvoiceReaderInterface;

public class InvoiceReader implements InvoiceReaderInterface {
	static String fileName;
	public static String emailFrmAddress;
	static int totalMessage;

	@SuppressWarnings("unused")
	@Override
	public int receiveEmail(String pop3Host, String mailStoreType, String userName, String password) {
		// Set properties
		Properties props = new Properties();
		props.put("mail.store.protocol", "pop3");
		props.put("mail.pop3.host", pop3Host);
		props.put("mail.pop3.port", "995");
		props.put("mail.pop3.starttls.enable", "true");

		// Get the Session object.
		Session session = Session.getInstance(props);
		try {
			// Create the POP3 store object and connect to the pop store.
			Store store = session.getStore("pop3s");
			store.connect(pop3Host, userName, password);

			// Create the folder object and open it in your mailbox.
			Folder emailFolder = store.getFolder("INBOX");
			emailFolder.open(Folder.READ_ONLY);

			// Retrieve the messages from the folder object.
			Message[] messages = emailFolder.getMessages();
			System.out.println("Total Message : " + messages.length);
			totalMessage = messages.length;
			// Iterate the messages
			for (int i = 0; i < messages.length; i++) {
				Message message = messages[i];
				javax.mail.Address[] toAddress = message.getRecipients(Message.RecipientType.TO);
				System.out.println("---------------------------------");
				System.out.println("Details of Email Message " + (i + 1) + " :");
				System.out.println("Subject: " + message.getSubject());
				System.out.println("From: " + message.getFrom()[0]);
				String temp = message.getFrom()[0] + "";
				emailFrmAddress = temp.substring(temp.indexOf('<') + 1, temp.indexOf('>'));
				// // Iterate recipients
				// System.out.println("To: ");
				// for (int j = 0; j < toAddress.length; j++) {
				// System.out.println(toAddress[j].toString());
				// }

				Multipart multipart = (Multipart) message.getContent();
				for (int k = 0; k < multipart.getCount(); k++) {

					BodyPart bodyPart = multipart.getBodyPart(k);
					if (bodyPart.getDisposition() != null
							&& bodyPart.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)) {
						fileName = bodyPart.getFileName();
						System.out.println("--" + fileName);
						System.out.println("file name " + bodyPart.getFileName());
						System.out.println("size " + bodyPart.getSize());
						System.out.println("content type " + bodyPart.getContentType());
						InputStream stream = (InputStream) bodyPart.getInputStream();
						File targetFile = new File("D://Workspace//pdf//" + bodyPart.getFileName());
						// System.out.println(fileName);
						java.nio.file.Files.copy(stream, targetFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
					}
				}
			}
			if (fileName != null) {
				ReadFile obj = new ReadFile();
				obj.FileReader(fileName);
			}

			emailFolder.close(false);
			store.close();
		} catch (MailConnectException e) {
			e.getMessage();
			System.out.println("=========================\nCannot connect to network\n=========================");
		} catch (MessagingException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return totalMessage;
	}

}
