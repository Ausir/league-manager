package it.unipd.dei.db.kayak.league_manager.data_utils;

import it.unipd.dei.db.kayak.league_manager.data.EventResult;

import java.util.Comparator;

public class EventResultTimeComparator implements Comparator<EventResult> {
	private boolean inverted;

	public EventResultTimeComparator(boolean inverted) {
		this.inverted = inverted;
	}

	public boolean isInverted() {
		return inverted;
	}

	@Override
	public int compare(EventResult o1, EventResult o2) {
		if (o1.getFraction() < o2.getFraction()) {
			return inverted ? -1 : 1;
		} else if (o1.getFraction() > o2.getFraction()) {
			return inverted ? 1 : -1;
		}

		if (o1.getInstant() < o2.getInstant()) {
			return inverted ? -1 : 1;
		} else if (o1.getInstant() > o2.getInstant()) {
			return inverted ? 1 : -1;
		}

		return 0;
	}
}
