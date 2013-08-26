package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.MatchDay;
import it.unipd.dei.db.kayak.league_manager.data.MatchDayDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchDayMatches;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;
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

		// prepare tournament heading
		String tCaption = "<span style = 'font-size:200%'>" 
				+ m_details.getMatchDay().getMatchDay().getTournamentName() + " " 
				+ m_details.getMatchDay().getMatchDay().getTournamentYear() + "</span>";
		mainLayout.addComponent(new Label(tCaption, ContentMode.HTML));
		
		// insert gap after tournament name
		Label gap = new Label();
		gap.setHeight("1em");
		mainLayout.addComponent(gap);

		VerticalLayout tLayout = new VerticalLayout();
		tLayout.setMargin(new MarginInfo(false, false, false, true));
		mainLayout.addComponent(tLayout);

		VerticalLayout mDayLayout = null;

		GridLayout grid = null;
		String phaseName = "";

		MatchDayDetails mDayDetails = m_details.getMatchDay();
		MatchDay day = mDayDetails.getMatchDay();

		// prepare match day caption
		String start_day = new SimpleDateFormat("dd").format((Date)day.getStartDate());
		String end_day = new SimpleDateFormat("dd/MM").format((Date)day.getEndDate());
		String caption = "<span style = 'font-size:130%'>" 
				+ start_day + "-" + end_day + " - <b>" + day.getName() + "</b> - " + mDayDetails.getLocationName() + "</span>" ;
		tLayout.addComponent(new Label(caption, ContentMode.HTML));

		mDayLayout = new VerticalLayout();
		mDayLayout.setMargin(new MarginInfo(false, false, false, true));

		tLayout.addComponent(mDayLayout);

		List<MatchUpResult> matches = m_details.getMatches();
		Collections.sort(matches,
				new MatchUpPhaseComparator());

		phaseName = "";
		// iterate over matches
		for (MatchUpResult mUpRes : matches) {
			// phase has changed, prepare
			if (!mUpRes.getTournamentPhaseName().equals(phaseName)) {

				phaseName = mUpRes.getTournamentPhaseName();
				mDayLayout.addComponent(new Label("<b>" + phaseName + "</b>", ContentMode.HTML));

				// set up a new grid
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

			// set up the magnifier icon button
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
			btn.setWidth("45");
			
			// fill the grid
			grid.addComponent(btn);
			grid.addComponent(new Label (mUpRes.getTeamHostName()));
			grid.addComponent(new Label (mUpRes.getTeamGuestName()));
			grid.addComponent(new Label (mUpRes.getTeamHostGoals() + "-" + mUpRes.getTeamGuestGoals()));
			grid.addComponent(new Label (new SimpleDateFormat("dd/MM").format((Date)mUpRes.getDate())));
			grid.addComponent(new Label (mUpRes.getTime().toString()));
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
