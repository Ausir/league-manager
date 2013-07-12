package it.unipd.dei.db.kayak.league_manager.data;

import java.sql.Timestamp;

public class MatchUpResult {
	// IDs (db) for the referenced tables
	private long matchUpID;
	private long matchDayID;
	private String tournamentPhaseName;
	private String tournamentName;
	private long tournamentYear;
	private long clubHostID;
	private long clubGuestID;

	private String teamHostName;
	private String teamGuestName;
	private int teamHostGoals;
	private int teamGuestGoals;
	private Timestamp time;

	public String getCompactString() {
		return teamHostName + " " + teamHostGoals + " - " + teamGuestName + " "
				+ teamGuestGoals + " " + time;
	}
}
