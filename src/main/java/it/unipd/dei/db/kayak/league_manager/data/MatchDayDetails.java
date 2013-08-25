package it.unipd.dei.db.kayak.league_manager.data;

public class MatchDayDetails {
	
	private MatchDay matchDay;
	private String organizerClubName;
	private String locationName;
	private int number;

	public MatchDayDetails(MatchDay matchDay, int number, String organizerClubName,
			String locationName) {
		super();
		this.matchDay = matchDay;
		this.organizerClubName = organizerClubName;
		this.locationName = locationName;
		this.number = number;
	}

	public MatchDay getMatchDay() {
		return matchDay;
	}
	
	public int getNumber(){
		return number;
	}

	public String getOrganizerClubName() {
		return organizerClubName;
	}

	public String getLocationName() {
		return locationName;
	}
}