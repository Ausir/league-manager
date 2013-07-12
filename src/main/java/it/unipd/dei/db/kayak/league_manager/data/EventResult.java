package it.unipd.dei.db.kayak.league_manager.data;

public class EventResult {
	// IDs (db) for the referenced tables
	private long eventID;
	private long matchUpID;
	private String actionName;

	private PlayerMatchUpInfo playerInfo;
	private int instant;
	private int fraction;
	private String actionDescription;

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
}
