package it.unipd.dei.db.kayak.league_manager.data_utils;

import it.unipd.dei.db.kayak.league_manager.data.PlayerMatchUpInfo;

import java.util.Comparator;

public class PlayerMatchUpInfoLineUpComparator implements
		Comparator<PlayerMatchUpInfo> {
	@Override
	public int compare(PlayerMatchUpInfo o1, PlayerMatchUpInfo o2) {
		if (o1.getNumber() > o2.getNumber()) {
			return 1;
		} else if (o1.getNumber() < o2.getNumber()) {
			return -1;
		}
		
		return 0;
	}
}
