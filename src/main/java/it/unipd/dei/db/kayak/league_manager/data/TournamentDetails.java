package it.unipd.dei.db.kayak.league_manager.data;

import java.util.List;
import java.util.Map;

public class TournamentDetails {
	private Tournament tournament;
	private List<MatchDayDetails> matchDayDetails;
	// index is md_num
	private Map<Integer, List<MatchUpResult>> matchUpResults;

	public TournamentDetails(Tournament tournament,
			List<MatchDayDetails> matchDayDetails,
			Map<Integer, List<MatchUpResult>> matchUpResults) {
		super();
		this.tournament = tournament;
		this.matchDayDetails = matchDayDetails;
		this.matchUpResults = matchUpResults;
	}

	public Tournament getTournament() {
		return tournament;
	}

	public List<MatchDayDetails> getMatchDayDetails() {
		return matchDayDetails;
	}

	public Map<Integer, List<MatchUpResult>> getMatchUpResults() {
		return matchUpResults;
	}
}
