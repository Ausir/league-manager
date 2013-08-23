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
	private String email;
	private String website;

	public Club(long id, String name, String shortName, String phone,
			String address, String email, String website) {
		super();
		this.id = id;
		this.name = name;
		this.shortName = shortName;
		this.phone = phone;
		this.address = address;
		this.email = email;
		this.website = website;
	}
	
	public String getCompactString(){
		String ret = "";
		ret += id + " ";
		ret += name + " ";
		ret += shortName + " ";
		ret += phone + " ";
		ret += address + " ";
		ret += email + " ";
		ret += website + "\n";
		return ret;
	}

	public long getID() {
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
	
	public String getEmail() {
		return email;
	}
	
	public String getWebsite() {
		return website;
	}
}
