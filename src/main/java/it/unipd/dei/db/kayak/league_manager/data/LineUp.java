package it.unipd.dei.db.kayak.league_manager.data;

public class LineUp {
	// CREATE TABLE lm.LineUp (
	// id BIGSERIAL,
	// game_ready BOOLEAN,
	// original_lineup ID_REF,
	// color_1 COLOR,
	// color_2 COLOR,
	// match_day STRING_IDENTIFIER,
	// manager EMAIL,
	// club_id INT,
	// PRIMARY KEY (id),
	// FOREIGN KEY (match_day) REFERENCES lm.MatchDay (id),
	// FOREIGN KEY (original_lineup) REFERENCES lm.LineUp (id),
	// FOREIGN KEY (manager) REFERENCES lm.ClubManager (user_email),
	// FOREIGN KEY (club_id) REFERENCES lm.Club (id)
	// );
	private long id;
	private boolean gameReady;
	private String color1;
	private String color2;
	private String matchDayID;
	private String managerEmail;
	private long clubID;

	public LineUp(long id, boolean gameReady, String color1, String color2,
			String matchDayID, String managerEmail, long clubID) {
		super();
		this.id = id;
		this.gameReady = gameReady;
		this.color1 = color1;
		this.color2 = color2;
		this.matchDayID = matchDayID;
		this.managerEmail = managerEmail;
		this.clubID = clubID;
	}

	public long getID() {
		return id;
	}

	public boolean isGameReady() {
		return gameReady;
	}

	public String getColor1() {
		return color1;
	}

	public String getColor2() {
		return color2;
	}

	public String getMatchDayID() {
		return matchDayID;
	}

	public String getManagerEmail() {
		return managerEmail;
	}

	public long getClubID() {
		return clubID;
	}
}
