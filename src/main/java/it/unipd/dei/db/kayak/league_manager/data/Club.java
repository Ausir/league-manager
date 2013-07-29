package it.unipd.dei.db.kayak.league_manager.data;

public class Club {
	// CREATE TABLE lm.Club (
	// id INT, -- provided by the federation
	// name LONG_NAME NOT NULL,
	// short_name VARCHAR(50),
	// phone_number PHONE,
	// address VARCHAR (300),
	// PRIMARY KEY (id)
	// );
	private long id;
	private String name;
	private String shortName;
	private String phone;
	private String address;

	public Club(long id, String name, String shortName, String phone,
			String address) {
		super();
		this.id = id;
		this.name = name;
		this.shortName = shortName;
		this.phone = phone;
		this.address = address;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getShortName() {
		return shortName;
	}

	public String getPhone() {
		return phone;
	}

	public String getAddress() {
		return address;
	}
}
