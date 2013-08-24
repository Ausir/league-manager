package it.unipd.dei.db.kayak.league_manager.data;

public class Action {
	// CREATE TABLE lm.Action (
	// name varchar(30), -- a short name defining the action
	// display_name VARCHAR(50), -- to be displayed to users
	// PRIMARY KEY (name)
	// );
	private String name;
	private String displayName;

	public Action(String name, String displayName) {
		super();
		this.name = name;
		this.displayName = displayName;
	}

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}
}
