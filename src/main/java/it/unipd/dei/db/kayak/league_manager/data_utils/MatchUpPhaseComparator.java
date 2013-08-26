package it.unipd.dei.db.kayak.league_manager.data_utils;

import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;

import java.util.Comparator;

// order by tournament phase number DESC
public class MatchUpPhaseComparator implements Comparator<MatchUpResult>{
	public int compare(MatchUpResult o1, MatchUpResult o2) {
		return o1.getTournamentPhaseNum() - o2.getTournamentPhaseNum();
	}
}
