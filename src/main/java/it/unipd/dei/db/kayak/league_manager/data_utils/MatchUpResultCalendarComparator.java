package it.unipd.dei.db.kayak.league_manager.data_utils;

import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;

import java.util.Comparator;

public class MatchUpResultCalendarComparator implements
		Comparator<MatchUpResult> {
	public int compare(MatchUpResult o1, MatchUpResult o2) {
		int ret = o2.getMatchDayStartDate().compareTo(o1.getMatchDayStartDate());
		if (ret == 0) {
			ret = o2.getMatchDayID().compareTo(o2.getMatchDayID());
			if (ret == 0) {
				ret = o2.getDate().compareTo(o1.getDate());
				if (ret == 0) {
					ret = o2.getTime().compareTo(o1.getTime());
				}
			}
		}
		return ret;
	}
}
