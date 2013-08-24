package it.unipd.dei.db.kayak.league_manager.data_utils;

import it.unipd.dei.db.kayak.league_manager.data.MatchDay;

import java.util.Comparator;

public class MatchDayCalendarComparator  implements Comparator<MatchDay> {
		public int compare(MatchDay o1, MatchDay o2) {
			int ret = o2.getEndDate().compareTo(o1.getStartDate());
			return ret;
		}
	}
