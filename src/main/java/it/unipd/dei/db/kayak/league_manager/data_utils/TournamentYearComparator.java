package it.unipd.dei.db.kayak.league_manager.data_utils;

import it.unipd.dei.db.kayak.league_manager.data.Tournament;

import java.util.Comparator;

public class TournamentYearComparator implements Comparator<Tournament> {
	private boolean increasing;

	public TournamentYearComparator(boolean increasing) {
		this.increasing = increasing;
	}

	@Override
	public int compare(Tournament o1, Tournament o2) {
		if (o1.getYear() < o2.getYear()) {
			return increasing ? -1 : 1;
		} else if (o1.getYear() > o2.getYear()) {
			return increasing ? 1 : -1;
		}

		int ret = o1.getName().compareTo(o2.getName());
		if (ret != 0) {
			return increasing ? ret : -ret;
		}

		if (o1.getMaxAge() > o2.getMaxAge()) {
			return increasing ? -1 : 1;
		} else if (o1.getMaxAge() < o2.getMaxAge()) {
			return increasing ? 1 : -1;
		}

		if (o1.getSex() != o2.getSex()) {
			if (o1.getSex()) {
				return increasing ? -1 : 1;
			} else {
				return increasing ? 1 : -1;
			}
		}

		return 0;
	}
}
