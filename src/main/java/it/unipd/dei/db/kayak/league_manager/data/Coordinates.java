package it.unipd.dei.db.kayak.league_manager.data;

public class Coordinates {
	// CREATE TABLE lm.Coordinates (
	// manager EMAIL,
	// club INT,
	// PRIMARY KEY (manager, club),
	// FOREIGN KEY (manager) REFERENCES lm.Manager (user_email),
	// FOREIGN KEY (club) REFERENCES lm.Club (id)
	// );
	private String managerEmail;
	private long clubID;

	public Coordinates(String managerEmail, long clubID) {
		super();
		this.managerEmail = managerEmail;
		this.clubID = clubID;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public long getClubID() {
		return clubID;
	}
}
