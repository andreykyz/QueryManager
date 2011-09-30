package ru.devhead;

import java.sql.*;
import java.util.ArrayList;
import java.io.IOException;
import java.io.PrintWriter;

//import org.postgresql.Driver;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//import org.apache.log4j.Logger;
//import org.apache.log4j.BasicConfigurator;

//import net.sf.json.JSONArray;


public class DoQuery extends HttpServlet {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//URL for the database connection
	String databaseURL = "jdbc:postgresql://localhost:5432/test_base";
	// Array of column names
	String driverName = "org.postgresql.Driver";
	String username = "tester";
	String password = "123";
	
//	protected final static Logger logger = Logger.getLogger(MainWorker.class);

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		BasicConfigurator.configure();
//		logger.info("Start MainWorker live cycle");
		
		PrintWriter out = resp.getWriter();

		String requestId = "125";//req.getParameter("requestid");
//		String query = req.getParameter("query");
		
//		out.println('[');
//		out.println("callback" + requestId + "({datatable:" + loadTableData("select * from schema1.table1") + "})");
		out.println("callback" + requestId + "({\'datatable\':[[\"collumn1\", \"collumn2\"], [\"fff, \"ffhf\"], [\"fff\", \"ffhf\"], [\"fff\", \"ffhf\"]]});");

//		out.println(']');
//		out.println("requestid->" + requestId);
//		out.println("query->    " + query);
		out.flush();
	}
	
	String loadTableData(String query) {
//		String[] columnNames;
		ArrayList<ArrayList<String>> tableData = new ArrayList<ArrayList<String>>();
		System.out.println(driverName);
		try {
			Class.forName(driverName);
		} catch (ClassNotFoundException e) {
	
			e.printStackTrace();
		}
		try {
			Connection conn = DriverManager.getConnection(databaseURL,
					username, password);
			Statement stmt = conn.createStatement(
					ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			ResultSet rs = stmt.executeQuery(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int numberOfColumns = rsmd.getColumnCount();
			// create new array
			tableData.add(new ArrayList<String>());
			// get the column names; column indexes start from 1
			for (int i = 1; i < numberOfColumns + 1; i++) {
//				logger.debug(rsmd.getColumnName(i));
				// [i-1] because array indexes start from 0
				tableData.get(0).add(rsmd.getColumnName(i));
				
			}
			// create new array of table data
			rs.last();
//			int numberOfRow = rs.getRow();
//			tableData = new String[numberOfRow][numberOfColumns];
			rs.beforeFirst();
//			logger.debug("numberOfRow = " + numberOfRow);
			// get the table data
			// row loop
			int j=1;
			while(rs.next()) {
//				logger.debug("Start get table data");
				tableData.add(new ArrayList<String>());
				for (int i = 1; i < numberOfColumns + 1; i++) {
//					logger.info(rs.getString(i));
//					tableData[i - 1][j - 1] = rs.getString(j);
					tableData.get(j).add(rs.getString(i));
//					logger.info((String) tableData[i - 1][j - 1]);
				}
				j++;
			}
//			logger.debug(tableData);
		} catch (SQLFeatureNotSupportedException e) {

			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return tableData.toString();
//		for (int i=0; i<tableData.size(); i++) {
//			jSONTableData.add(new JSONArray(jSONTableData.get));
//		}
		
	}
	
}
