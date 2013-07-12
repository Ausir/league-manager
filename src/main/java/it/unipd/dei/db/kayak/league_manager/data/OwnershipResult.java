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
}
