package it.unipd.dei.db.kayak.league_manager.data;

import java.util.List;

public class PlayerCareerInfo {
	// IDs (db) for the referenced tables
	// private long playerID;

	private Player playerData;
	private List<OwnershipResult> ownerships;
	// private List<PlayerTournamentStatistics> tournamentResults;
	private List<PlayerCareerEvent> careerEvents;

	public PlayerCareerInfo(// long playerID,
			Player playerData, List<OwnershipResult> ownerships,
			// List<PlayerTournamentStatistics> tournamentResults,
			List<PlayerCareerEvent> careerEvents) {
		super();
		// this.playerID = playerID;
		this.playerData = playerData;
		// this.tournamentResults = tournamentResults;
		this.ownerships = ownerships;
		this.careerEvents = careerEvents;
	}

	// public long getPlayerID() {
	// return playerID;
	// }

	public Player getPlayerData() {
		return playerData;
	}

	public List<OwnershipResult> getOwnerships() {
		return ownerships;
	}

	// public List<PlayerTournamentStatistics> getTournamentResults() {
	// return tournamentResults;
	// }

	public List<PlayerCareerEvent> getCareerEvents() {
		return careerEvents;
	}
}
