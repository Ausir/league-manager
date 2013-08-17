package it.unipd.dei.db.kayak.league_manager.data;

import java.util.List;

public class PlayerCareerInfo {
	// IDs (db) for the referenced tables
	private long playerID;
	
	private String playerName;
	private List<EventResult> events;
	private List<OwnershipResult> ownerships;
	
	public PlayerCareerInfo(long playerID, String playerName,
			List<EventResult> events, List<OwnershipResult> ownerships) {
		super();
		this.playerID = playerID;
		this.playerName = playerName;
		this.events = events;
		this.ownerships = ownerships;
	}
	
	public long getPlayerID() {
		return playerID;
	}
	public String getPlayerName() {
		return playerName;
	}
	public List<EventResult> getEvents() {
		return events;
	}
	public List<OwnershipResult> getOwnerships() {
		return ownerships;
	}
	
	public String getCompactString(){
		String ret;
		ret = playerName;
		for(EventResult er: events){
			ret += "\n" + er.getCompactString();
		}
		for(OwnershipResult or: ownerships){
			ret += "\n" + or.getCompactString();
		}
		return ret;
	}
}
