package it.unipd.dei.db.kayak.league_manager.data;

public class PlayerTournamentStatistics {
	private long playerID;
	private String tournamentName;
	private int tournamentYear;
	private long clubID;

	private String clubName;
	private int playedMatches;
	private int goals;
	private int yellowCards;
	private int greenCards;
	private int redCards;

	public PlayerTournamentStatistics(long playerID, String tournamentName,
			int tournamentYear, long clubID, String clubName,
			int playedMatches, int goals, int yellowCards, int greenCards,
			int redCards) {
		super();
		this.playerID = playerID;
		this.tournamentName = tournamentName;
		this.tournamentYear = tournamentYear;
		this.clubID = clubID;
		this.clubName = clubName;
		this.playedMatches = playedMatches;
		this.goals = goals;
		this.yellowCards = yellowCards;
		this.greenCards = greenCards;
		this.redCards = redCards;
	}

	public long getPlayerID() {
		return playerID;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public int getTournamentYear() {
		return tournamentYear;
	}

	public long getClubID() {
		return clubID;
	}

	public String getClubName() {
		return clubName;
	}

	public int getPlayedMatches() {
		return playedMatches;
	}

	public int getGoals() {
		return goals;
	}

	public int getYellowCards() {
		return yellowCards;
	}

	public int getGreenCards() {
		return greenCards;
	}

	public int getRedCards() {
		return redCards;
	}
}
