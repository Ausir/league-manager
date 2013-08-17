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
	private long matchID;
	private long lineupHostID;
	private long lineupGuestID;

	public Plays(long matchID, long lineupHostID, long lineupGuestID) {
		super();
		this.matchID = matchID;
		this.lineupHostID = lineupHostID;
		this.lineupGuestID = lineupGuestID;
	}

	public long getMatchUpID() {
		return matchID;
	}

	public long getLineupHostID() {
		return lineupHostID;
	}

	public long getLineUpGuestID() {
		return lineupGuestID;
	}
}
