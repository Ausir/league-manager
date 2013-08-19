package it.unipd.dei.db.kayak.league_manager.data;

public class CallsUp {
	// CREATE TABLE lm.CallsUp (
	// lineup_id ID_REF,
	// ownership_id ID_REF,
	// club_id INT,
	// player_number INT NOT NULL,
	// PRIMARY KEY (lineup_id, ownership_id, club_id),
	// FOREIGN KEY (lineup_id) REFERENCES lm.LineUp (id),
	// FOREIGN KEY (ownership_id) REFERENCES lm.Ownership (id),
	// FOREIGN KEY (club_id) REFERENCES lm.Club (id)
	// );
	private String lineupID;
	private long ownershipID;
	private long clubID;
	private int playerNumber;

	public CallsUp(String lineupID, long ownershipID, long clubID,
			int playerNumber) {
		super();
		this.lineupID = lineupID;
		this.ownershipID = ownershipID;
		this.clubID = clubID;
		this.playerNumber = playerNumber;
	}

	public String getLineupID() {
		return lineupID;
	}

	public long getOwnershipID() {
		return ownershipID;
	}

	public long getClubID() {
		return clubID;
	}

	public int getPlayerNumber() {
		return playerNumber;
	}
}
