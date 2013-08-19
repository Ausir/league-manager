package it.unipd.dei.db.kayak.league_manager.data;

public class EventResult {
	// IDs (db) for the referenced tables
	private long eventID;
	private String matchUpID;
	private String actionName;

	private PlayerMatchUpInfo playerInfo;
	private int instant;
	private int fraction;
	private String actionDescription;

	public EventResult(long eventID, String matchUpID, String actionName,
			PlayerMatchUpInfo playerInfo, int instant, int fraction,
			String actionDescription) {
		super();
		this.eventID = eventID;
		this.matchUpID = matchUpID;
		this.actionName = actionName;
		this.playerInfo = playerInfo;
		this.instant = instant;
		this.fraction = fraction;
		this.actionDescription = actionDescription;
	}

	public String getCompactString() {
		String time;
		switch (fraction) {
		case 0:
			time = "first half";
			break;
		case 1:
			time = "second half";
			break;
		case 2:
			time = "overtime";
			break;
		default:
			time = "error 404: fraction not found";
		}

		return "" + instant + " " + time + " " + actionDescription + " "
				+ playerInfo.getCompactString();
	}

	public String getShortString() {
		String time;
		switch (fraction) {
		case 0:
			time = "first half";
			break;
		case 1:
			time = "second half";
			break;
		case 2:
			time = "overtime";
			break;
		default:
			time = "error 404: fraction not found";
		}

		return "" + instant + "' " + time + " " + actionDescription + " "
				+ playerInfo.getNumber() + " " + playerInfo.getPlayerName();
	}

	public long getEventID() {
		return eventID;
	}

	public String getMatchUpID() {
		return matchUpID;
	}

	public String getActionName() {
		return actionName;
	}

	public PlayerMatchUpInfo getPlayerInfo() {
		return playerInfo;
	}

	public int getInstant() {
		return instant;
	}

	public int getFraction() {
		return fraction;
	}

	public String getActionDescription() {
		return actionDescription;
	}
}
