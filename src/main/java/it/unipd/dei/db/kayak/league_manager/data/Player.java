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
	private String name;
	private Date birthday;

	public Player(long id, String name, Date birthday) {
		super();
		this.id = id;
		this.name = name;
		this.birthday = birthday;
	}

	public long getID() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Date getBirthday() {
		return birthday;
	}
	
	public String getCompactString(){
		String ret = "";
		ret += id + " ";
		ret += name + " ";
		ret += birthday + "\n";
		return ret;
	}
}
