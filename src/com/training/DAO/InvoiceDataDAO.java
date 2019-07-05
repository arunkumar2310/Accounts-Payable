package com.training.DAO;

import java.sql.*;
import java.util.Properties;

import com.training.model.InvoiceData;

@SuppressWarnings("unused")
public class InvoiceDataDAO {
	private static String callString;
	private static CallableStatement cs;
	public static Connection connection;

	public static Connection connectDB() throws Exception {
		String userName = "hr";
		String password = "hr";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";

		Properties connectionProps = new Properties();
		connectionProps.put("user", userName);
		connectionProps.put("password", password);
		connection = DriverManager.getConnection(url, connectionProps);

		return connection;
	}

	public static void insertData(InvoiceData invoiceData) throws Exception {
		try {
			Connection connection = connectDB();
			String sqlQuery = "INSERT INTO INVOICEDATAS (INVOICE_DATE,CUSTOMER_PO,AMOUNT,INVOICE_NUM,EMAIL_FROM_ADDRESS,SOLD_TO_ADDRESS,SHIP_TO_ADDRESS,REMIT_TO_ADDRESS,APPROVAL_STATE) VALUES (?,?,?,?,?,?,?,?,?) ";
			PreparedStatement statement = connection.prepareStatement(sqlQuery);
			statement.setString(1, invoiceData.getInvoiceDate());
			statement.setString(2, invoiceData.getCustomerPO());
			statement.setString(3, invoiceData.getAmount());
			statement.setString(4, invoiceData.getInvoiceNum());
			statement.setString(5, invoiceData.getEmailFrmAddress() + "");
			statement.setString(6, invoiceData.getSoldToAddress());
			statement.setString(7, invoiceData.getShipToAddress());
			statement.setString(8, invoiceData.getRemitToAddress());
			statement.setString(9, invoiceData.getState());
			int result = statement.executeUpdate();
			if (result > 0) {
				System.out.println("Data inserted sucessfully");
			}
		} catch (SQLIntegrityConstraintViolationException e) {
			System.out.println("Primary key value should be unique. Can not update into DB.\n");
		} catch (SQLException e) {
			e.printStackTrace();
			System.err.println("Failed to connect to database" + e);
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

}
