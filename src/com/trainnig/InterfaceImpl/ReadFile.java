package com.trainnig.InterfaceImpl;

import java.io.File;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.training.DAO.InvoiceDataDAO;
import com.training.Interface.FileReaderInterface;
import com.training.model.InvoiceData;


public class ReadFile implements FileReaderInterface {

	@Override
	@SuppressWarnings(value = { "" })
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

					} else if (lines[i].equals("Invoice Date")) {
						data.setInvoiceDate(lines[i + 1]);
					}

					else if (lines[i].equals("Customer P.O.")) {
						data.setCustomerPO(lines[i + 1]);
					}

					else if (lines[i].equals("Total Invoice")) {
						data.setAmount(lines[i + 1]);
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
					}
					data.setEmailFrmAddress(InvoiceReader.emailFrmAddress);
					if (!data.getAmount().isEmpty() && !data.getCustomerPO().isEmpty()
							&& !data.getInvoiceDate().isEmpty() && !data.getInvoiceDate().isEmpty()
							&& !data.getInvoiceNum().isEmpty() && !data.getRemitToAddress().isEmpty()
							&& !data.getShipToAddress().isEmpty()) {
						System.out.println("Invoice number: \n" + data.getInvoiceNum() + "\n\nInvoice date: \n"
								+ data.getInvoiceDate() + "\n\nCustomer PO: \n" + data.getCustomerPO()
								+ "\n\nSold To: \n" + data.getSoldToAddress() + "\nShip To: \n"
								+ data.getShipToAddress() + "\nRemit To: \n" + data.getRemitToAddress()
								+ "\nTotal Amount: \n" + data.getAmount());
						System.out.println("======================================");
						InvoiceDataDAO.insertData(data);
						data = new InvoiceData();
						shiftToAddress = remitToAddress = soldToAddress = "";
					}
				}
			}
		}
	}

}
