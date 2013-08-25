package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.MatchDayDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;
import it.unipd.dei.db.kayak.league_manager.data.Tournament;
import it.unipd.dei.db.kayak.league_manager.data.TournamentDetails;
import it.unipd.dei.db.kayak.league_manager.data_utils.MatchDayDetailsCalendarComparator;
import it.unipd.dei.db.kayak.league_manager.data_utils.MatchUpPhaseComparator;

import java.io.File;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

import com.vaadin.server.FileResource;
import com.vaadin.server.VaadinService;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

public class TournamentCalendarViewer {
	// private fields
	private VerticalLayout mainLayout;
	private TournamentDetails tournamentDetails;

	// constructor
	public TournamentCalendarViewer(TournamentDetails tournamentDetails) {
		this.tournamentDetails = tournamentDetails;

		// order match days by date
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
		
		String tCaption = tournament.getName() + " " + tournament.getYear()
				+ " max age: " + tournament.getMaxAge() + " ";
		tCaption += tournament.getSex();
		mainLayout.addComponent(new Label(tCaption));

		VerticalLayout tLayout = new VerticalLayout();
		tLayout.setMargin(new MarginInfo(false, false, false, true));
		mainLayout.addComponent(tLayout);

		VerticalLayout mDayLayout = null;
		String mDayID = "";
		int mDayNum;

		int mUpIdx = 0;
		VerticalLayout phaseLayout = null;
		GridLayout grid = null;
		String phaseName = "";

		for (int i = 0; i < tournamentDetails.getMatchDayDetails().size(); ++i) {
			MatchDayDetails mDayDetails = tournamentDetails
					.getMatchDayDetails().get(i);
			mDayID = mDayDetails.getMatchDay().getID();
			mDayNum = mDayDetails.getMatchDay().getNum();
			
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

			List<MatchUpResult> matches = tournamentDetails.getMatchUpResults().get(mDayNum);
			Collections.sort(matches,
					new MatchUpPhaseComparator());
			
			phaseName = "";
			
			for (MatchUpResult mUpRes : matches) {
				if (!mUpRes.getTournamentPhaseName().equals(phaseName)) {
										
					phaseName = mUpRes.getTournamentPhaseName();
					mDayLayout.addComponent(new Label("<b>" + phaseName + "</b>", ContentMode.HTML));

					//phaseLayout = new VerGridLayout ticalLayout();
					//phaseLayout.setMargin(new MarginInfo(false, false, false,
					//		true));

					grid = new GridLayout(6,1);
					mDayLayout.addComponent(grid);
					mDayLayout.setSpacing(true);
					grid.setWidth("70%");

					grid.setColumnExpandRatio(0, 0.2f);
					grid.setColumnExpandRatio(1, 5);
					grid.setColumnExpandRatio(2, 5);
					grid.setColumnExpandRatio(3, 1);
					grid.setColumnExpandRatio(4, 1);
					grid.setColumnExpandRatio(5, 1);
					
					
					
					grid.setMargin(new MarginInfo(false, false, false, true));
				}

				//HorizontalLayout resultLine = new HorizontalLayout();

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

				//resultLine.addComponent(btn);

				//Label smallSpacer = new Label("");
				//smallSpacer.setWidth("10px");
				//resultLine.addComponent(smallSpacer);

				//Label resultLabel = new Label(mUpRes.getCompactString());
				//resultLine.addComponent(resultLabel);
				//resultLine.setExpandRatio(resultLabel, 1);

				//phaseLayout.addComponent(resultLine);
				btn.setWidth("45");
				grid.addComponent(btn);
				grid.addComponent(new Label (mUpRes.getTeamHostName()));
				grid.addComponent(new Label (mUpRes.getTeamGuestName()));
				grid.addComponent(new Label (mUpRes.getTeamHostGoals() + "-" + mUpRes.getTeamGuestGoals()));
				grid.addComponent(new Label (new SimpleDateFormat("dd/MM").format((Date)mUpRes.getDate())));
				grid.addComponent(new Label (mUpRes.getTime().toString()));
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
