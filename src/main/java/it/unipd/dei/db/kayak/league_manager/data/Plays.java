package it.unipd.dei.db.kayak.league_manager.data;

public class Plays {
	// CREATE TABLE lm.Plays (
	// match_id ID_REF,
	// lineup_host ID_REF,
	// lineup_guest ID_REF,
	// PRIMARY KEY (match_id, lineup_host, lineup_guest),
	// FOREIGN KEY (match_id) REFERENCES lm.MatchUp (id),
	// FOREIGN KEY (lineup_host) REFERENCES lm.LineUp (id),
	// FOREIGN KEY (lineup_guest) REFERENCES lm.LineUp (id)
	// );
	private String matchID;
	private String lineupHostID;
	private String lineupGuestID;

	public Plays(String matchID, String lineupHostID, String lineupGuestID) {
		super();
		this.matchID = matchID;
		this.lineupHostID = lineupHostID;
		this.lineupGuestID = lineupGuestID;
	}

	public String getMatchUpID() {
		return matchID;
	}

	public String getLineupHostID() {
		return lineupHostID;
	}

	public String getLineUpGuestID() {
		return lineupGuestID;
	}
}
