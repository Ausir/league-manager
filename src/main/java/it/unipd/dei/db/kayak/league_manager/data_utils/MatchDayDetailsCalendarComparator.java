package it.unipd.dei.db.kayak.league_manager.data_utils;

import it.unipd.dei.db.kayak.league_manager.data.MatchDayDetails;

import java.util.Comparator;

public class MatchDayDetailsCalendarComparator implements
		Comparator<MatchDayDetails> {
	public int compare(MatchDayDetails o1, MatchDayDetails o2) {
		int ret = o2.getMatchDay().getEndDate()
				.compareTo(o1.getMatchDay().getStartDate());
		return ret;
	}
}
