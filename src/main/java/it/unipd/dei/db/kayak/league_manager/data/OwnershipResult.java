package it.unipd.dei.db.kayak.league_manager.data;

import java.sql.Date;

public class OwnershipResult {
	// IDs (db) for the referenced tables
	private long ownershipID;
	private long playerID;
	
	private String clubName;
	private Date start;
	private Date end;
	private boolean borrowed;
	
	public OwnershipResult(long ownershipID, long playerID, String clubName,
			Date start, Date end, boolean borrowed) {
		super();
		this.ownershipID = ownershipID;
		this.playerID = playerID;
		this.clubName = clubName;
		this.start = start;
		this.end = end;
		this.borrowed = borrowed;
	}
	
	public long getOwnershipID() {
		return ownershipID;
	}
	public long getPlayerID() {
		return playerID;
	}
	public String getClubName() {
		return clubName;
	}
	public Date getStart() {
		return start;
	}
	public Date getEnd() {
		return end;
	}
	public boolean isBorrowed() {
		return borrowed;
	}
}
