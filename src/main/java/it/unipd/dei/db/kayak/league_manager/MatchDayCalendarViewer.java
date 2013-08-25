package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.MatchDayDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchDayMatches;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;
import it.unipd.dei.db.kayak.league_manager.data.Tournament;
import it.unipd.dei.db.kayak.league_manager.data.TournamentDetails;
import it.unipd.dei.db.kayak.league_manager.data_utils.MatchDayDetailsCalendarComparator;
import it.unipd.dei.db.kayak.league_manager.data_utils.MatchUpPhaseComparator;

import java.io.File;
import java.util.Collections;
import java.util.List;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class MatchDayCalendarViewer {
	// private fields
	private VerticalLayout mainLayout;
	private MatchDayMatches m_details;

	// constructor
	public MatchDayCalendarViewer(MatchDayMatches m_details) {
		this.m_details = m_details;
		this.setUpContent();
	}

	private void setUpContent() {
		mainLayout = new VerticalLayout();
		mainLayout.setMargin(new MarginInfo(true, true, true, true));


		String tCaption = m_details.getMatchDay().getMatchDay().getTournamentName() + " " + m_details.getMatchDay().getMatchDay().getTournamentYear();
		mainLayout.addComponent(new Label(tCaption));

		VerticalLayout tLayout = new VerticalLayout();
		tLayout.setMargin(new MarginInfo(false, false, false, true));
		mainLayout.addComponent(tLayout);

		VerticalLayout mDayLayout = null;
		String mDayID = "";
		int mDayNum;

		int mUpIdx = 0;
		VerticalLayout phaseLayout = null;
		String phaseName = "";

		MatchDayDetails mDayDetails = m_details.getMatchDay();
		mDayID = mDayDetails.getMatchDay().getID();
		mDayNum = mDayDetails.getMatchDay().getNum();

		String dayString = mDayDetails.getMatchDay().getStartDate().toString();
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

		List<MatchUpResult> matches = m_details.getMatches();
		Collections.sort(matches,
				new MatchUpPhaseComparator());

		phaseName = "";
		for (MatchUpResult mUpRes : matches) {
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
			Button btn = new Button("", new ClickListener() {
				@Override
				public void buttonClick(ClickEvent event) {
					Home home = ((MyVaadinUI) UI.getCurrent()).getHome();
					home.showMatchUpDetailsSubWindow(mUpID);
				}
			});

			String basepath = VaadinService.getCurrent().getBaseDirectory()
					.getAbsolutePath();
			FileResource resource = new FileResource(new File(basepath
					+ "/WEB-INF/images/magnifier.png"));
			btn.setIcon(resource);

			resultLine.addComponent(btn);

			Label smallSpacer = new Label("");
			smallSpacer.setWidth("10px");
			resultLine.addComponent(smallSpacer);

			Label resultLabel = new Label(mUpRes.getCompactString());
			resultLine.addComponent(resultLabel);
			resultLine.setExpandRatio(resultLabel, 1);

			phaseLayout.addComponent(resultLine);
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
