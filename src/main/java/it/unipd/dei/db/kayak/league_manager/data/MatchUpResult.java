package it.unipd.dei.db.kayak.league_manager.data;

import java.sql.Date;
import java.sql.Time;

public class MatchUpResult {
	// IDs (db) for the referenced tables
	private String matchUpID;
	private String matchDayID;
	private String tournamentPhaseName;
	private String tournamentName;
	private long tournamentYear;
	private long clubHostID;
	private long clubGuestID;

	private Date date;
	private String teamHostName;
	private String teamGuestName;
	private int teamHostGoals;
	private int teamGuestGoals;
	private Time time;

	public MatchUpResult(String matchUpID, String matchDayID,
			String tournamentPhaseName, String tournamentName,
			long tournamentYear, long clubHostID, long clubGuestID, Date date,
			String teamHostName, String teamGuestName, int teamHostGoals,
			int teamGuestGoals, Time time) {
		super();
		this.matchUpID = matchUpID;
		this.matchDayID = matchDayID;
		this.tournamentPhaseName = tournamentPhaseName;
		this.tournamentName = tournamentName;
		this.tournamentYear = tournamentYear;
		this.clubHostID = clubHostID;
		this.clubGuestID = clubGuestID;
		this.date = date;
		this.teamHostName = teamHostName;
		this.teamGuestName = teamGuestName;
		this.teamHostGoals = teamHostGoals;
		this.teamGuestGoals = teamGuestGoals;
		this.time = time;
	}

	public String getCompactString() {
		return teamHostName + " " + teamHostGoals + " - " + teamGuestName + " "
				+ teamGuestGoals + " " + time;
	}

	public String getFullyQualifiedName() {
		return this.getTournamentName() + " " + this.getTournamentYear()
				+ " - " + this.getTournamentPhaseName() + ": "
				+ this.getTeamHostName() + " " + this.getTeamHostGoals() + " "
				+ this.getTeamGuestName() + " " + this.getTeamGuestGoals();
	}

	public String getMatchUpID() {
		return matchUpID;
	}

	public String getMatchDayID() {
		return matchDayID;
	}

	public String getTournamentPhaseName() {
		return tournamentPhaseName;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public long getTournamentYear() {
		return tournamentYear;
	}

	public long getClubHostID() {
		return clubHostID;
	}

	public long getClubGuestID() {
		return clubGuestID;
	}

	public Date getDate() {
		return date;
	}

	public String getTeamHostName() {
		return teamHostName;
	}

	public String getTeamGuestName() {
		return teamGuestName;
	}

	public int getTeamHostGoals() {
		return teamHostGoals;
	}

	public int getTeamGuestGoals() {
		return teamGuestGoals;
	}

	public Time getTime() {
		return time;
	}
}
