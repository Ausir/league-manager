package it.unipd.dei.db.kayak.league_manager.data;

public class Tournament {
	// CREATE TABLE lm.Tournament (
	// name LONG_NAME,
	// year SMALLINT,
	// max_age INT,
	// sex BOOLEAN,
	// organizer EMAIL,
	// PRIMARY KEY (year, name),
	// FOREIGN KEY (organizer) REFERENCES lm.Manager (user_email)
	// );
	private String name;
	private int year;
	private int maxAge;
	private boolean sex;
	private String organizerEmail;

	public Tournament(String name, int year, int maxAge, boolean sex,
			String organizerEmail) {
		super();
		this.name = name;
		this.year = year;
		this.maxAge = maxAge;
		this.sex = sex;
		this.organizerEmail = organizerEmail;
	}

	public String getName() {
		return name;
	}

	public int getYear() {
		return year;
	}

	public int getMaxAge() {
		return maxAge;
	}

	/**
	 * Return true if tournament is for males, false for females
	 * 
	 * @return
	 */
	public boolean getSex() {
		return sex;
	}

	public String getOrganizerEmail() {
		return organizerEmail;
	}
	
	public String getCompactString(){
		String ret;
		ret = name + " " + year + " " +maxAge + (sex==true?" male":" female");
		return ret;
	}
}
