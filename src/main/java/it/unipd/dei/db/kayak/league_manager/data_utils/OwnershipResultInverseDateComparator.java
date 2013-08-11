package it.unipd.dei.db.kayak.league_manager.data_utils;

import it.unipd.dei.db.kayak.league_manager.data.OwnershipResult;

import java.util.Comparator;

public class OwnershipResultInverseDateComparator implements
		Comparator<OwnershipResult> {
	@Override
	public int compare(OwnershipResult o1, OwnershipResult o2) {
		return o2.getStart().compareTo(o1.getStart());
	}
}
