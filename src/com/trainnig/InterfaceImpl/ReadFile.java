package com.trainnig.InterfaceImpl;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.training.Bean.InvoiceData;
import com.training.DAO.InvoiceDataDAO;
import com.training.Interface.FileReaderInterface;

public class ReadFile implements FileReaderInterface {

	@Override
	public void FileReader(String fileName) throws Exception {
		try (PDDocument document = PDDocument.load(new File("D:/Workspace/pdf/" + fileName))) {

			document.getClass();

			if (!document.isEncrypted()) {
				PDFTextStripper textStripper = new PDFTextStripper();
				String pdfFileInText = textStripper.getText(document);
				// split by whitespace
				String lines[] = pdfFileInText.split("\\r?\\n");

				String address;
				int index;
				String shiftToAddress = "";
				String soldToAddress = "";
				String remitToAddress = "";

				InvoiceData data = new InvoiceData();

				for (int i = 0; i < lines.length; i++) {
					if (lines[i].equals("Invoice No")) {
						data.setInvoiceNum(lines[i + 1]);
						System.out.println("Invoice number: " + lines[i + 1]);

					} else if (lines[i].equals("Invoice Date")) {
						data.setInvoiceDate(lines[i + 1]);
						System.out.println("Invoice date: " + lines[i + 1]);
					}

					else if (lines[i].equals("Customer P.O.")) {
						data.setCustomerPO(lines[i + 1]);
						System.out.println("Customer PO: " + lines[i + 1]);
					}

					else if (lines[i].equals("Total Invoice")) {
						data.setAmount(lines[i + 1]);
						System.out.println("Total invoice amount: " + lines[i + 1]);
					}

					else if (lines[i].equals("Sold To")) {
						index = i + 1;
						address = lines[index];
						while (!(address.equals("Ship To"))) {
							index++;
							soldToAddress += address + "\n";
							address = lines[index];
						}
						data.setSoldToAddress(soldToAddress);
						System.out.println("Sold To: " + lines[i + 1]);
					}

					else if (lines[i].equals("Ship To")) {
						index = i + 1;
						address = lines[index];
						while (!(address.equals("Remit To"))) {
							index++;
							shiftToAddress += address + "\n";
							address = lines[index];
						}
						data.setShipToAddress(shiftToAddress);
						System.out.println("Ship To: " + lines[i + 1]);
					}

					else if (lines[i].equals("Remit To")) {
						index = i + 1;
						address = lines[index];
						while (!(address.equals("Payment Terms"))) {
							index++;
							remitToAddress += address + "\n";
							address = lines[index];
						}
						data.setRemitToAddress(remitToAddress);
						System.out.println("Remit To: " + lines[i + 1]);
					}
					data.setEmailFrmAddress(InvoiceReader.emailFrmAddress);
					if (!data.getAmount().isEmpty() && !data.getCustomerPO().isEmpty()
							&& !data.getInvoiceDate().isEmpty() && !data.getInvoiceDate().isEmpty()
							&& !data.getInvoiceNum().isEmpty() && !data.getRemitToAddress().isEmpty()
							&& !data.getShipToAddress().isEmpty()) {
						System.out.println("======================================");
						InvoiceDataDAO.insertData(data);
						data = new InvoiceData();
					}
				}
			}
		}
	}

}
