package it.unipd.dei.db.kayak.league_manager.data;

public class PlayerMatchUpInfo {
	// IDs (db) for the referenced tables
	private long playerID;
	private long matchUpID;
	private long clubID;
	
	private String playerName;
	private String clubName;
	private int number;

	public PlayerMatchUpInfo(long playerID, long matchUpID, long clubID,
			String playerName, String clubName, int number) {
		super();
		this.playerID = playerID;
		this.matchUpID = matchUpID;
		this.clubID = clubID;
		this.playerName = playerName;
		this.clubName = clubName;
		this.number = number;
	}

	public String getCompactString() {
		return clubName + " " + number + " " + playerName;
	}

	public long getPlayerID() {
		return playerID;
	}

	public long getMatchUpID() {
		return matchUpID;
	}

	public long getClubID() {
		return clubID;
	}

	public String getPlayerName() {
		return playerName;
	}

	public String getClubName() {
		return clubName;
	}

	public int getNumber() {
		return number;
	}
}
