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

	public MatchUpDetails(long lineUpHostID, long lineUpGuestID,
			String pitchName, long locationID, MatchUpResult result,
			String locationName, List<EventResult> eventList,
			List<PlayerMatchUpInfo> hostLineUp,
			List<PlayerMatchUpInfo> guestLineUp) {
		super();
		this.lineUpHostID = lineUpHostID;
		this.lineUpGuestID = lineUpGuestID;
		this.pitchName = pitchName;
		this.locationID = locationID;
		this.result = result;
		this.locationName = locationName;
		this.eventList = eventList;
		this.hostLineUp = hostLineUp;
		this.guestLineUp = guestLineUp;
	}

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

	public long getLineUpHostID() {
		return lineUpHostID;
	}

	public long getLineUpGuestID() {
		return lineUpGuestID;
	}

	public String getPitchName() {
		return pitchName;
	}

	public long getLocationID() {
		return locationID;
	}

	public MatchUpResult getResult() {
		return result;
	}

	public String getLocationName() {
		return locationName;
	}

	public List<EventResult> getEventList() {
		return eventList;
	}

	public List<PlayerMatchUpInfo> getHostLineUp() {
		return hostLineUp;
	}

	public List<PlayerMatchUpInfo> getGuestLineUp() {
		return guestLineUp;
	}
}
