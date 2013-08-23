package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.MatchDayDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;
import it.unipd.dei.db.kayak.league_manager.data.Tournament;
import it.unipd.dei.db.kayak.league_manager.data.TournamentDetails;
import it.unipd.dei.db.kayak.league_manager.data_utils.MatchDayDetailsCalendarComparator;
import it.unipd.dei.db.kayak.league_manager.data_utils.MatchUpResultCalendarComparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class TournamentCalendarViewer {
	// private fields
	private VerticalLayout mainLayout;
	private ArrayList<VerticalLayout> dayLayoutList;

	private TournamentDetails tournamentDetails;

	// constructor
	public TournamentCalendarViewer(TournamentDetails tournamentDetails) {
		this.tournamentDetails = tournamentDetails;

		Collections.sort(tournamentDetails.getMatchUpResults(),
				new MatchUpResultCalendarComparator());

		Collections.sort(tournamentDetails.getMatchDayDetails(),
				new MatchDayDetailsCalendarComparator());

		this.setUpContent();
	}

	private void setUpContent() {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(new MarginInfo(true, true, true, true));

		Tournament tournament = tournamentDetails.getTournament();
		List<MatchDayDetails> matchDayDetails = tournamentDetails
				.getMatchDayDetails();
		List<MatchUpResult> matchUpResults = tournamentDetails
				.getMatchUpResults();

		String tCaption = tournament.getName() + " " + tournament.getYear()
				+ " max age: " + tournament.getMaxAge() + " ";
		tCaption += tournament.getSex() ? "M" : "F";
		mainLayout.addComponent(new Label(tCaption));

		VerticalLayout tLayout = new VerticalLayout();
		tLayout.setMargin(new MarginInfo(false, false, false, true));
		mainLayout.addComponent(tLayout);

		VerticalLayout mDayLayout = null;
		String mDayID = "";

		int mUpIdx = 0;
		VerticalLayout phaseLayout = null;
		String phaseName = "";

		for (int i = 0; i < tournamentDetails.getMatchDayDetails().size(); ++i) {
			MatchDayDetails mDayDetails = tournamentDetails
					.getMatchDayDetails().get(i);
			mDayID = mDayDetails.getMatchDay().getID();

			String dayString = "Day "
					+ (tournamentDetails.getMatchDayDetails().size() - i)
					+ ": " + mDayDetails.getMatchDay().getStartDate();
			if (mDayDetails.getMatchDay().getStartDate()
					.compareTo(mDayDetails.getMatchDay().getEndDate()) != 0) {
				dayString += " - " + mDayDetails.getMatchDay().getEndDate();
			}

			dayString += " managed by " + mDayDetails.getOrganizerClubName()
					+ " at " + mDayDetails.getLocationName();
			tLayout.addComponent(new Label(dayString));

			mDayLayout = new VerticalLayout();
			mDayLayout.setMargin(new MarginInfo(false, false, false, true));

			tLayout.addComponent(mDayLayout);

			phaseName = "";

			for (; mUpIdx < tournamentDetails.getMatchUpResults().size(); ++mUpIdx) {
				MatchUpResult mUpRes = tournamentDetails.getMatchUpResults()
						.get(mUpIdx);

				if (!mDayID.equals(mUpRes.getMatchDayID())) {
					break;
				}

				if (!mUpRes.getTournamentPhaseName().equals(phaseName)) {
					phaseName = mUpRes.getTournamentPhaseName();

					mDayLayout.addComponent(new Label(phaseName));

					phaseLayout = new VerticalLayout();
					phaseLayout.setMargin(new MarginInfo(false, false, false,
							true));

					mDayLayout.addComponent(phaseLayout);
				}

				HorizontalLayout resultLine = new HorizontalLayout();

				final String mUpID = mUpRes.getMatchUpID();
				resultLine.addComponent(new Button("View Match",
						new ClickListener() {
							@Override
							public void buttonClick(ClickEvent event) {
								Home home = ((MyVaadinUI) UI.getCurrent())
										.getHome();
								home.showMatchUpDetailsSubWindow(mUpID);
							}
						}));
				
				Label smallSpacer = new Label("");
				smallSpacer.setWidth("10px");
				resultLine.addComponent(smallSpacer);

				Label resultLabel = new Label(mUpRes.getCompactString());
				resultLine.addComponent(resultLabel);
				resultLine.setExpandRatio(resultLabel, 1);
				
				phaseLayout.addComponent(resultLine);
			}
		}

		Label spacer = new Label();
		mainLayout.addComponent(spacer);
		mainLayout.setExpandRatio(spacer, 1);

		mainLayout.setSizeFull();
	}

	public VerticalLayout getContent() {
		return mainLayout;
	}
}
