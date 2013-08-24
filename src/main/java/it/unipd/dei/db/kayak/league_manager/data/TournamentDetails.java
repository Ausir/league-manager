package it.unipd.dei.db.kayak.league_manager.data;

import java.util.List;

public class TournamentDetails {
	private Tournament tournament;
	private List<MatchDayDetails> matchDayDetails;
	private List<MatchUpResult> matchUpResults;

	public TournamentDetails(Tournament tournament,
			List<MatchDayDetails> matchDayDetails,
			List<MatchUpResult> matchUpResults) {
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

	public List<MatchUpResult> getMatchUpResults() {
		return matchUpResults;
	}
}
