package it.unipd.dei.db.kayak.league_manager.data;

import java.sql.Date;

public class Ownership {
	// CREATE TABLE lm.Ownership(
	// id BIGSERIAL,
	// player_id INT,
	// club_id INT,
	// isBorrowed BOOLEAN,
	// start_date DATE,
	// end_date DATE,
	// PRIMARY KEY (id),
	// FOREIGN KEY (player_id) REFERENCES lm.Player(id),
	// FOREIGN KEY (club_id) REFERENCES lm.Club(id)
	private long id;
	private long playerID;
	private long clubID;
	private boolean borrowed;
	private Date startDate;
	private Date endDate;

	public Ownership(long id, long playerID, long clubID, boolean borrowed,
			Date startDate, Date endDate) {
		super();
		this.id = id;
		this.playerID = playerID;
		this.clubID = clubID;
		this.borrowed = borrowed;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public long getID() {
		return id;
	}

	public long getPlayerID() {
		return playerID;
	}

	public long getClubID() {
		return clubID;
	}

	public boolean isBorrowed() {
		return borrowed;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Date getEndDate() {
		return endDate;
	}
}
