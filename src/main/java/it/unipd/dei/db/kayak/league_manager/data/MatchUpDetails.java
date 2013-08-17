package it.unipd.dei.db.kayak.league_manager.data;

import it.unipd.dei.db.kayak.league_manager.data_utils.EventResultTimeComparator;
import it.unipd.dei.db.kayak.league_manager.data_utils.PlayerMatchUpInfoLineUpComparator;

import java.util.ArrayList;
import java.util.Collections;
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
	private String lineman1;
	private String lineman2;
	private String timekeeper1;
	private String timekeeper2;
	private String scorekeeper;
	private String referee1;
	private String referee2;

	public MatchUpDetails(long lineUpHostID, long lineUpGuestID,
			String pitchName, long locationID, MatchUpResult result,
			String locationName, List<EventResult> eventList,
			List<PlayerMatchUpInfo> hostLineUp,
			List<PlayerMatchUpInfo> guestLineUp, String lineman1,
			String lineman2, String timekeeper1, String timekeeper2,
			String scorekeeper, String referee1, String referee2) {
		super();
		this.lineUpHostID = lineUpHostID;
		this.lineUpGuestID = lineUpGuestID;
		this.pitchName = pitchName;
		this.locationID = locationID;
		this.result = result;
		this.locationName = locationName;
		this.eventList = new ArrayList<EventResult>(eventList);
		Collections.sort(this.eventList, new EventResultTimeComparator(false));
		PlayerMatchUpInfoLineUpComparator playerComp = new PlayerMatchUpInfoLineUpComparator();
		this.hostLineUp = new ArrayList<PlayerMatchUpInfo>(hostLineUp);
		Collections.sort(this.hostLineUp, playerComp);
		this.guestLineUp = new ArrayList<PlayerMatchUpInfo>(guestLineUp);
		Collections.sort(this.guestLineUp, playerComp);
		this.lineman1 = lineman1;
		this.lineman2 = lineman2;
		this.timekeeper1 = timekeeper1;
		this.timekeeper2 = timekeeper2;
		this.scorekeeper = scorekeeper;
		this.referee1 = referee1;
		this.referee2 = referee2;
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

	public String getLineman1() {
		return lineman1;
	}

	public String getLineman2() {
		return lineman2;
	}

	public String getTimekeeper1() {
		return timekeeper1;
	}

	public String getTimekeeper2() {
		return timekeeper2;
	}

	public String getScorekeeper() {
		return scorekeeper;
	}

	public String getReferee1() {
		return referee1;
	}

	public String getReferee2() {
		return referee2;
	}
}
