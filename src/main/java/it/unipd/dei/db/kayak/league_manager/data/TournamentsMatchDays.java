package it.unipd.dei.db.kayak.league_manager.data;

import java.util.List;
import java.util.Map;

public class TournamentsMatchDays {
	private Map<Tournament, List<MatchDay>> matchDays;
	
	public TournamentsMatchDays (Map<Tournament, List<MatchDay>> matchDays) {
		this.matchDays = matchDays;
	}

	public Map<Tournament, List<MatchDay>> getMatchDays() {
		return matchDays;
	}
}
