package it.unipd.dei.db.kayak.league_manager.data;

import java.sql.Date;

public class Subscription {
	// CREATE TABLE lm.Subscritpion (
	// club INT,
	// tournament_name LONG_NAME,
	// tournament_year SMALLINT,
	// registeration_date DATE NOT NULL, -- DATE is a reserved SQL keyword
	// PRIMARY KEY (club, tournament_year, tournament_name),
	// FOREIGN KEY (club) REFERENCES lm.Club (id),
	// FOREIGN KEY (tournament_year, tournament_name) REFERENCES lm.Tournament
	// (year, name)
	// );
	private long clubID;
	private String tournamentName;
	private int tournamentYear;
	private Date registrationDate;

	public Subscription(long clubID, String tournamentName, int tournamentYear,
			Date registrationDate) {
		super();
		this.clubID = clubID;
		this.tournamentName = tournamentName;
		this.tournamentYear = tournamentYear;
		this.registrationDate = registrationDate;
	}

	public long getClubID() {
		return clubID;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public int getTournamentYear() {
		return tournamentYear;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}
}
