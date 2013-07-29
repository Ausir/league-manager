package it.unipd.dei.db.kayak.league_manager.data;

import java.sql.Date;

public class MatchDay {
	// CREATE TABLE lm.MatchDay (
	// id STRING_IDENTIFIER, -- a value created by the start date, day number,
	// location and tournament
	// num INT NOT NULL, -- number is a reserved keyword
	// start_date DATE NOT NULL,
	// end_date DATE NOT NULL,
	// club INT,
	// location ID_REF NOT NULL,
	// tournament_name LONG_NAME NOT NULL,
	// tournament_year SMALLINT NOT NULL,
	// PRIMARY KEY (id),
	// FOREIGN KEY (club) REFERENCES lm.Club (id),
	// FOREIGN KEY (location) REFERENCES lm.Location (id),
	// FOREIGN KEY (tournament_year, tournament_name) REFERENCES lm.Tournament
	// (year, name)
	// );
	private String id;
	private int num;
	private Date startDate;
	private Date endDate;
	private long clubID;
	private long locationID;
	private String tournamentName;
	private int tournamentYear;

	public MatchDay(String id, int num, Date startDate, Date endDate,
			long clubID, long locationID, String tournamentName,
			int tournamentYear) {
		super();
		this.id = id;
		this.num = num;
		this.startDate = startDate;
		this.endDate = endDate;
		this.clubID = clubID;
		this.locationID = locationID;
		this.tournamentName = tournamentName;
		this.tournamentYear = tournamentYear;
	}

	public String getId() {
		return id;
	}

	public int getNum() {
		return num;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public long getClubID() {
		return clubID;
	}

	public long getLocationID() {
		return locationID;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public int getTournamentYear() {
		return tournamentYear;
	}
}
