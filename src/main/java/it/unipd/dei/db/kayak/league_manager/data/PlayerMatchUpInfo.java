package it.unipd.dei.db.kayak.league_manager.data;

public class PlayerMatchUpInfo {
	// IDs (db) for the referenced tables
	private long playerID;
	private long matchUpID;
	private long clubID;
	
	private String playerName;
	private String clubName;
	private int number;

	public String getCompactString() {
		return clubName + " " + number + " " + playerName;
	}
}
