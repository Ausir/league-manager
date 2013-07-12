package it.unipd.dei.db.kayak.league_manager.data;

import java.util.List;

public class PlayerCareerInfo {
	// IDs (db) for the referenced tables
	private long playerID;
	
	private String playerName;
	private List<EventResult> events;
	private List<OwnershipResult> ownerships;
}
