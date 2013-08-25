package it.unipd.dei.db.kayak.league_manager.data;

public class MatchDayDetails {
	
	private MatchDay matchDay;
	private String organizerClubName;
	private String locationName;

	public MatchDayDetails(MatchDay matchDay, String organizerClubName,
			String locationName) {
		super();
		this.matchDay = matchDay;
		this.organizerClubName = organizerClubName;
		this.locationName = locationName;
	}

	public MatchDay getMatchDay() {
		return matchDay;
	}

	public String getOrganizerClubName() {
		return organizerClubName;
	}

	public String getLocationName() {
		return locationName;
	}
}