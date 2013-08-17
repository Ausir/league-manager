package it.unipd.dei.db.kayak.league_manager.data;

import java.sql.Date;

public class LMUser {
	// CREATE TABLE lm.LMUser (
	// user_email EMAIL,
	// password PASSWORD,
	// phone_number PHONE,
	// first_name COMMON_NAME,
	// last_name COMMON_NAME,
	// birthday DATE,
	// PRIMARY KEY (user_email)
	// );
	private String email;
	private byte[] password;
	private String phone;
	private String firstName;
	private String lastName;
	private Date birthday;

	public LMUser(String email, byte[] password, String phone,
			String firstName, String lastName, Date birthday) {
		super();
		this.email = email;
		this.password = password;
		this.phone = phone;
		this.firstName = firstName;
		this.lastName = lastName;
		this.birthday = birthday;
	}

	public String getEmail() {
		return email;
	}

	public byte[] getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
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
