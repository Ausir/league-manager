package it.unipd.dei.db.kayak.league_manager.data;

import java.util.List;

public class MatchDayMatches {

	private MatchDayDetails matchDay;
	private List<MatchUpResult> matches;
	
	public MatchDayMatches(MatchDayDetails matchDay, List<MatchUpResult> results) {
		this.matchDay = matchDay;
		this.matches = results;
	}

	public MatchDayDetails getMatchDay() {
		return matchDay;
	}

	public List<MatchUpResult> getMatches() {
		return matches;
	}	
}
