package it.unipd.dei.db.kayak.league_manager.data;

import java.sql.Date;

public class Player {
	// CREATE TABLE lm.Player (
	// id INT, -- provided by the federation
	// first_name COMMON_NAME NOT NULL,
	// last_name COMMON_NAME NOT NULL,
	// birthday DATE NOT NULL,
	// PRIMARY KEY (id)
	// );
	private long id;
	private String firstName;
	private String lastName;
	private Date birthday;

	public Player(long id, String firstName, String lastName, Date birthday) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
	}

	public long getID() {
		return id;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getBirthday() {
		return birthday;
	}
}
