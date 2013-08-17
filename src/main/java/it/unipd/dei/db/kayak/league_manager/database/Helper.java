package it.unipd.dei.db.kayak.league_manager.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Helper {
	
	final static String URL = "jdbc:postgresql://localhost:5555/Kayak";
	final static String USER = "Kayak";
	final static String PASSWORD = "aijaevau";

	static String readFileAsString(String filePath) throws IOException {
		StringBuffer fileData = new StringBuffer();
		BufferedReader reader = new BufferedReader(new FileReader(filePath));
		char[] buf = new char[1024000];
		int numRead = 0;
		while ((numRead = reader.read(buf)) != -1) {
			String readData = String.valueOf(buf, 0, numRead);
			fileData.append(readData + "\n");
		}
		reader.close();
		return fileData.toString();
	}

	public static String getVersion() {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;
	
		String ret = "";
	
		/*
		 * se ottengo questo errore: SEVERE: FATAL: password authentication
		 * failed for user "user12"
		 * 
		 * bisogna impostare la password all'user: sudo -u postgres psql
		 * postgres=# ALTER USER user12 WITH PASSWORD '34klq*';
		 */
	
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			st = con.createStatement();
			rs = st.executeQuery("SELECT VERSION()");
	
			if (rs.next()) {
				ret = rs.getString(1);
			}
	
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DDL.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);
	
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (st != null) {
					st.close();
				}
				if (con != null) {
					con.close();
				}
	
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DDL.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
		return ret;
	}

}
