package it.unipd.dei.db.kayak.league_manager.data;

import java.util.List;

public class PlayerCareerInfo {
	// IDs (db) for the referenced tables
	private long playerID;

	private Player playerData;
	private List<EventResult> events;
	private List<OwnershipResult> ownerships;

	public PlayerCareerInfo(long playerID, Player playerData,
			List<EventResult> events, List<OwnershipResult> ownerships) {
		super();
		this.playerID = playerID;
		this.playerData = playerData;
		this.events = events;
		this.ownerships = ownerships;
	}

	public long getPlayerID() {
		return playerID;
	}

	public Player getPlayerData() {
		return playerData;
	}

	public List<EventResult> getEvents() {
		return events;
	}

	public List<OwnershipResult> getOwnerships() {
		return ownerships;
	}
}
