package it.unipd.dei.db.kayak.league_manager.data;

import java.sql.Date;
import java.sql.Time;

public class PlayerCareerEvent {
	private String tournamentName;
	private int tournamentYear;
	private String matchDayID;
	private String matchUpID;
	private long playerClubID;
	private long hostClubID;
	private long guestClubID;
	private long eventID;

	private String playerClubName;
	private Date matchDayStartDate;
	private Date matchDayEndDate;
	private Date matchUpDate;
	private Time matchUpTime;
	private String hostClubName;
	private String guestClubName;
	private int hostGoals;
	private int guestGoals;
	private int instant;
	private int fraction;
	private String actionDisplay;

	public PlayerCareerEvent(String tournamentName, int tournamentYear,
			String matchDayID, String matchUpID, long playerClubID,
			long hostClubID, long guestClubID, long eventID,
			String playerClubName, Date matchDayStartDate,
			Date matchDayEndDate, Date matchUpDate, Time matchUpTime,
			String hostClubName, String guestClubName, int hostGoals,
			int guestGoals, int instant, int fraction, String actionDisplay) {
		super();
		this.tournamentName = tournamentName;
		this.tournamentYear = tournamentYear;
		this.matchDayID = matchDayID;
		this.matchUpID = matchUpID;
		this.playerClubID = playerClubID;
		this.hostClubID = hostClubID;
		this.guestClubID = guestClubID;
		this.eventID = eventID;
		this.playerClubName = playerClubName;
		this.matchDayStartDate = matchDayStartDate;
		this.matchDayEndDate = matchDayEndDate;
		this.matchUpDate = matchUpDate;
		this.matchUpTime = matchUpTime;
		this.hostClubName = hostClubName;
		this.guestClubName = guestClubName;
		this.hostGoals = hostGoals;
		this.guestGoals = guestGoals;
		this.instant = instant;
		this.fraction = fraction;
		this.actionDisplay = actionDisplay;
	}

	public Date getMatchUpDate() {
		return matchUpDate;
	}

	public Time getMatchUpTime() {
		return matchUpTime;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public int getTournamentYear() {
		return tournamentYear;
	}

	public String getMatchDayID() {
		return matchDayID;
	}

	public String getMatchUpID() {
		return matchUpID;
	}

	public long getPlayerClubID() {
		return playerClubID;
	}

	public long getHostClubID() {
		return hostClubID;
	}

	public long getGuestClubID() {
		return guestClubID;
	}

	public long getEventID() {
		return eventID;
	}

	public String getPlayerClubName() {
		return playerClubName;
	}

	public Date getMatchDayStartDate() {
		return matchDayStartDate;
	}

	public Date getMatchDayEndDate() {
		return matchDayEndDate;
	}

	public String getHostClubName() {
		return hostClubName;
	}

	public String getGuestClubName() {
		return guestClubName;
	}

	public int getHostGoals() {
		return hostGoals;
	}

	public int getGuestGoals() {
		return guestGoals;
	}

	public int getInstant() {
		return instant;
	}

	public int getFraction() {
		return fraction;
	}

	public String getActionDisplay() {
		return actionDisplay;
	}
}
