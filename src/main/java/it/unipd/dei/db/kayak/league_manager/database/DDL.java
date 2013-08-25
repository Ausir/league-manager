package it.unipd.dei.db.kayak.league_manager.database;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

// operazioni sui metadati
public class DDL {

	public static void destroyTables() {
		DDL.executeSQLFile("SQL_Destroy.sql");
	}

	public static void createTables() {
		DDL.executeSQLFile("SQL_Create.sql");
	}

	static void executeSQLFile(String namefile) {
		Connection con = null;
		Statement st = null;
		ResultSet rs = null;

		// String url = "jdbc:postgresql://localhost:63333/Kayak";
		// String user = "Kayak";
		// String password = "aijaevau";
		String sql = "";

		FileInputStream in = null;
		try {
			sql = Helper.readFileAsString(namefile);
		} catch (IOException ex) {
			Logger lgr = Logger.getLogger(DDL.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				Logger lgr = Logger.getLogger(DDL.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}

		// sql =
		// "﻿-- color as #FFFFFF, without the hash\nCREATE DOMAIN COLOR AS CHAR(6);\n-- emails, arbitrarly established as 50 chars long\nCREATE DOMAIN EMAIL AS VARCHAR(50);";
		System.out.println(sql);

		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);
			st = con.createStatement();

			st.executeUpdate(sql);

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
	}
}
