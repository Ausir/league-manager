package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.Club;
import it.unipd.dei.db.kayak.league_manager.data.FakeDataWarehouse;
import it.unipd.dei.db.kayak.league_manager.data.Location;
import it.unipd.dei.db.kayak.league_manager.data.MatchDay;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;
import it.unipd.dei.db.kayak.league_manager.data.Tournament;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class TournamentCalendarViewer {
	// private fields
	private VerticalLayout mainLayout;
	private ArrayList<VerticalLayout> dayLayoutList;

	private Tournament tournament;
	private List<MatchUpResult> matchUpResults;
	private List<MatchDay> matchDays;

	private class MatchUpResultCalendarComparator implements
			Comparator<MatchUpResult> {
		public int compare(MatchUpResult o1, MatchUpResult o2) {
			int ret = o2.getDate().compareTo(o1.getDate());
			if (ret == 0) {
				ret = o1.getTournamentPhaseName().compareTo(
						o2.getTournamentPhaseName());
				if (ret == 0) {
					ret = o1.getTime().compareTo(o2.getTime());
				}
			}
			return ret;
		}

	}

	private class MatchDayCalendarComparator implements Comparator<MatchDay> {
		public int compare(MatchDay o1, MatchDay o2) {
			int ret = o2.getEndDate().compareTo(o1.getStartDate());
			return ret;
		}

	}

	// constructor
	public TournamentCalendarViewer(Tournament tournament) {
		this.tournament = tournament;

		matchUpResults = FakeDataWarehouse.getTournamentMatchUpResults(
				tournament.getName(), tournament.getYear());
		Collections.sort(matchUpResults, new MatchUpResultCalendarComparator());
		// for (MatchUpResult mRes : matchUpResults) {
		// System.out.println(mRes.getCompactString());
		// }

		matchDays = new ArrayList<MatchDay>(FakeDataWarehouse.getMatchDays());
		for (int i = 0; i < matchDays.size();) {
			MatchDay mDay = matchDays.get(i);
			if (mDay.getTournamentName() != tournament.getName()
					|| mDay.getTournamentYear() != tournament.getYear()) {
				matchDays.remove(i);
			} else {
				i++;
			}
		}

		Collections.sort(matchDays, new MatchDayCalendarComparator());
		// for (int i = 0; i < matchDays.size(); i++) {
		// MatchDay mDay = matchDays.get(i);
		// System.out.println("" + mDay.getStartDate() + " - "
		// + mDay.getEndDate());
		// }

		this.setUpContent();
	}

	private void setUpContent() {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(new MarginInfo(true, true, true, true));

		String tournamentCaption = tournament.getName() + " "
				+ tournament.getYear() + " max age: " + tournament.getMaxAge()
				+ " ";
		tournamentCaption += tournament.getSex() ? "M" : "F";
		mainLayout.addComponent(new Label(tournamentCaption));

		VerticalLayout subLayout = new VerticalLayout();
		subLayout.setMargin(new MarginInfo(true, true, true, true));

		dayLayoutList = new ArrayList<VerticalLayout>();

		int mDayIdx = 0;
		MatchDay mDay;
		VerticalLayout dayLayout;
		String dayString;
		// Label dayLabel;
		VerticalLayout dayBody;
		String tPhaseName;
		VerticalLayout phaseLayout;
		Label phaseLabel;
		VerticalLayout phaseBody = null;
		int mUpResIdx = 0;
		MatchUpResult mUpRes;
		// Label mUpResLabel;

		for (; mDayIdx < matchDays.size(); mDayIdx++) {
			mDay = matchDays.get(mDayIdx);

			dayLayout = new VerticalLayout();
			dayString = "Day " + (matchDays.size() - mDayIdx) + ": "
					+ mDay.getStartDate();
			if (mDay.getStartDate().compareTo(mDay.getEndDate()) != 0) {
				dayString += " - " + mDay.getEndDate();
			}
			Club organizer = null;
			for (Club c : FakeDataWarehouse.getClubs()) {
				if (c.getID() == mDay.getClubID()) {
					organizer = c;
					break;
				}
			}
			Location location = null;
			for (Location l : FakeDataWarehouse.getLocations()) {
				if (l.getID() == mDay.getLocationID()) {
					location = l;
					break;
				}
			}
			// dayLayout.addComponent(new Label("day managed by "
			// + organizer.getName() + " at " + location.getName()));
			dayString += " managed by " + organizer.getName() + " at "
					+ location.getName();
			// dayLabel = new Label(dayString);
			dayLayout.addComponent(new Label(dayString));

			dayBody = new VerticalLayout();
			dayBody.setMargin(new MarginInfo(false, false, false, true));
			dayLayout.addComponent(dayBody);

			subLayout.addComponent(dayLayout);
			dayLayoutList.add(dayLayout);

			tPhaseName = "";
			for (; mUpResIdx < matchUpResults.size(); mUpResIdx++) {
				mUpRes = matchUpResults.get(mUpResIdx);
				if (mUpRes.getMatchDayID() != mDay.getID()) {
					break;
				}
				if (tPhaseName != mUpRes.getTournamentPhaseName()) {
					tPhaseName = mUpRes.getTournamentPhaseName();
					phaseLayout = new VerticalLayout();
					phaseLabel = new Label(tPhaseName);
					phaseLayout.addComponent(phaseLabel);
					phaseBody = new VerticalLayout();
					phaseBody.setMargin(new MarginInfo(false, false, false,
							true));
					phaseLayout.addComponent(phaseBody);

					dayBody.addComponent(phaseLayout);
				}

				// mUpResLabel = new Label(mUpRes.getCompactString());
				// phaseBody.addComponent(mUpResLabel);
				// final MatchUpResult fmUpRes = mUpRes;
				final String fmatchUpID = mUpRes.getMatchUpID();
				Button mUpResBtn = new Button(mUpRes.getCompactString(),
						new ClickListener() {
							private static final long serialVersionUID = 9042873981140452659L;

							// MatchUpDetails details = FakeDataWarehouse
							// .getMatchUpDetails((int) fmUpRes
							// .getMatchUpID());
							// MatchUpDetailsSubWindow detailsWindow = new
							// MatchUpDetailsSubWindow(
							// details);
							// String matchUpID = fmUpRes.getMatchUpID();
							String matchUpID = fmatchUpID;

							@Override
							public void buttonClick(ClickEvent event) {
								Home home = ((MyVaadinUI) UI.getCurrent())
										.getHome();
								home.showMatchUpDetailsSubWindow(matchUpID);
							}
						});

				phaseBody.addComponent(mUpResBtn);
			}
		}

		mainLayout.addComponent(subLayout);
		// subLayout.setSizeFull();

		Label spacer = new Label();
		mainLayout.addComponent(spacer);
		mainLayout.setExpandRatio(spacer, 1);

		mainLayout.setSizeFull();
	}

	public VerticalLayout getContent() {
		return mainLayout;
	}
}
