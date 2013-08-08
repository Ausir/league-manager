package it.unipd.dei.db.kayak.league_manager.data_utils;

import it.unipd.dei.db.kayak.league_manager.data.EventResult;

import java.util.Comparator;

public class EventResultTimeComparator implements Comparator<EventResult> {
	@Override
	public int compare(EventResult o1, EventResult o2) {
		if (o1.getFraction() < o2.getFraction()) {
			return 1;
		} else if (o1.getFraction() > o2.getFraction()) {
			return -1;
		}

		if (o1.getInstant() < o2.getInstant()) {
			return 1;
		} else if (o1.getInstant() > o2.getInstant()) {
			return -1;
		}

		return 0;
	}
}
