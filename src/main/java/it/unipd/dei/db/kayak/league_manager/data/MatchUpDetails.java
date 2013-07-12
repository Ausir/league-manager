package it.unipd.dei.db.kayak.league_manager.data;

import java.util.List;

public class MatchUpDetails {
	// IDs (db) for the referenced tables
	private long lineUpHostID;
	private long lineUpGuestID;
	private String pitchName;
	private long locationID;

	private MatchUpResult result;
	private String locationName;
	private List<EventResult> eventList;
	private List<PlayerMatchUpInfo> hostLineUp;
	private List<PlayerMatchUpInfo> guestLineUp;

	public String getCompactString() {
		String ret = result.getCompactString() + " " + locationName + "\n";
		ret += "Host lineup:\n";
		for (PlayerMatchUpInfo player : hostLineUp) {
			ret += (player.getCompactString() + "\n");
		}
		ret += "Guest lineup:\n";
		for (PlayerMatchUpInfo player : guestLineUp) {
			ret += (player.getCompactString() + "\n");
		}
		for (EventResult event : eventList) {
			ret += (event.getCompactString() + "\n");
		}

		return ret;
	}
}
