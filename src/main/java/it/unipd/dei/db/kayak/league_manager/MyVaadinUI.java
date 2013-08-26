package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.database.Helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {
	private Home home;
	private Connection con;

	@Override
	public void init(VaadinRequest request) {
		home = new Home();
		addDetachListener(new DetachListener() {
			@Override
			public void detach(DetachEvent event) {
				home.close();
			}
		});
		this.setContent(home.getContent());
	}

	public Home getHome() {
		return home;
	}

	public void initConnection() {
		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public Connection getConnection(){
		return con;
	}

	public void closeConnection(){
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}