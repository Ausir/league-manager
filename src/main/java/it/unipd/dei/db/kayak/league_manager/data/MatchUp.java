package it.unipd.dei.db.kayak.league_manager.data;

import java.sql.Date;
import java.sql.Time;

public class MatchUp {
	// CREATE TABLE lm.MatchUp ( -- MATCH is a reserved SQL keyword
	// id string,
	// start_date DATE NOT NULL, -- meaning day of the year -- DATE is a
	// reserved SQL keyword
	// start_time TIME NOT NULL, -- meaning time_of_day -- TIME is a reserved
	// SQL keyword
	// match_day STRING_IDENTIFIER NOT NULL,
	// tournament_phase_name VARCHAR(50) NOT NULL,
	// tournament_name LONG_NAME NOT NULL,
	// tournament_phase_year SMALLINT NOT NULL,
	// lineman1 COMMON_NAME,
	// lineman2 COMMON_NAME,
	// timekeeper1 COMMON_NAME NOT NULL,
	// timekeeper2 COMMON_NAME,
	// scorekeeper COMMON_NAME NOT NULL,
	// referee1 COMMON_NAME NOT NULL,
	// referee2 COMMON_NAME,
	// pitch_name LONG_NAME NOT NULL,
	// pitch_location ID_REF NOT NULL,
	// goals_host SMALLINT, -- redundant properties storing the results for the
	// match
	// goals_guest SMALLINT, -- added for efficiency purposes
	// PRIMARY KEY (id),
	// FOREIGN KEY
	// (tournament_phase_name, tournament_phase_year, tournament_name)
	// REFERENCES lm.TournamentPhase (name, tournament_year, tournament_name),
	// FOREIGN KEY (match_day) REFERENCES lm.MatchDay (id),
	// FOREIGN KEY (pitch_name, pitch_location) REFERENCES lm.Pitch (name,
	// location)
	// );
	private String id;
	private Date startDate;
	private Time startTime;
	private String matchDayID;
	private String tournamentPhaseName;
	private String tournamentName;
	private int tournamentPhaseYear;
	private String lineman1;
	private String lineman2;
	private String timekeeper1;
	private String timekeeper2;
	private String scorekeeper;
	private String referee1;
	private String referee2;
	private String pitchName;
	private long pitchLocationID;
	private int goalsHost;
	private int goalsGuest;

	public MatchUp(String id, Date startDate, Time startTime, String matchDayID,
			String tournamentPhaseName, String tournamentName,
			int tournamentPhaseYear, String lineman1, String lineman2,
			String timekeeper1, String timekeeper2, String scorekeeper,
			String referee1, String refereee2, String pitchName,
			long pitchLocationID, int goalsHost, int goalsGuest) {
		super();
		this.id = id;
		this.startTime = startTime;
		this.startDate = startDate;
		this.matchDayID = matchDayID;
		this.tournamentPhaseName = tournamentPhaseName;
		this.tournamentName = tournamentName;
		this.tournamentPhaseYear = tournamentPhaseYear;
		this.lineman1 = lineman1;
		this.lineman2 = lineman2;
		this.timekeeper1 = timekeeper1;
		this.timekeeper2 = timekeeper2;
		this.scorekeeper = scorekeeper;
		this.referee1 = referee1;
		this.referee2 = refereee2;
		this.pitchName = pitchName;
		this.pitchLocationID = pitchLocationID;
		this.goalsHost = goalsHost;
		this.goalsGuest = goalsGuest;
	}

	public String getID() {
		return id;
	}

	public Date getStartDate() {
		return startDate;
	}

	public Time getStartTime() {
		return startTime;
	}

	public String getMatchDayID() {
		return matchDayID;
	}

	public String getTournamentPhaseName() {
		return tournamentPhaseName;
	}

	public String getTournamentName() {
		return tournamentName;
	}

	public int getTournamentPhaseYear() {
		return tournamentPhaseYear;
	}

	public String getLineman1() {
		return lineman1;
	}

	public String getLineman2() {
		return lineman2;
	}

	public String getTimekeeper1() {
		return timekeeper1;
	}

	public String getTimekeeper2() {
		return timekeeper2;
	}

	public String getScorekeeper() {
		return scorekeeper;
	}

	public String getReferee1() {
		return referee1;
	}

	public String getReferee2() {
		return referee2;
	}

	public String getPitchName() {
		return pitchName;
	}

	public long getPitchLocationID() {
		return pitchLocationID;
	}

	public int getGoalsHost() {
		return goalsHost;
	}

	public int getGoalsGuest() {
		return goalsGuest;
	}
}
