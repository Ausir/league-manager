package it.unipd.dei.db.kayak.league_manager.data;

public class Pitch {
	// CREATE TABLE lm.Pitch (
	// name LONG_NAME,
	// location ID_REF,
	// PRIMARY KEY (name, location),
	// FOREIGN KEY (location) REFERENCES lm.Location (id)
	// );
	private long locationID;
	private String name;

	public Pitch(long locationID, String name) {
		super();
		this.locationID = locationID;
		this.name = name;
	}

	public long getLocationID() {
		return locationID;
	}

	public String getName() {
		return name;
	}
}
