package it.unipd.dei.db.kayak.league_manager.data;

public class Location {
	// CREATE TABLE lm.Location (
	// id BIGSERIAL,
	// city LONG_NAME NOT NULL,
	// name LONG_NAME NOT NULL,
	// PRIMARY KEY (id)
	// );
	private long id;
	private String city;
	private String name;

	public Location(long id, String city, String name) {
		super();
		this.id = id;
		this.city = city;
		this.name = name;
	}

	public long getID() {
		return id;
	}

	public String getCity() {
		return city;
	}

	public String getName() {
		return name;
	}
}
