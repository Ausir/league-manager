package it.unipd.dei.db.kayak.league_manager;

import it.unipd.dei.db.kayak.league_manager.data.Club;
import it.unipd.dei.db.kayak.league_manager.data.EventResult;
import it.unipd.dei.db.kayak.league_manager.data.MatchDay;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;
import it.unipd.dei.db.kayak.league_manager.data.OwnershipResult;
import it.unipd.dei.db.kayak.league_manager.data.Player;
import it.unipd.dei.db.kayak.league_manager.data.PlayerCareerInfo;
import it.unipd.dei.db.kayak.league_manager.data.Tournament;
import it.unipd.dei.db.kayak.league_manager.database.DML;

import java.util.Date;
import java.util.List;

import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;

/**
 * Main UI class
 */
@SuppressWarnings("serial")
public class MyVaadinUI extends UI {

	@Override
	public void init(VaadinRequest request) {
	    // Create the content root layout for the UI
	    VerticalLayout content = new VerticalLayout();
	    setContent(content);

	    // Add a simple component
	    Label label = new Label("Hello Vaadin user");
	    content.addComponent(label);

	    // Add a component with user interaction
	    content.addComponent(new Button("What is the time?",
	                                    new ClickListener() {
	        public void buttonClick(ClickEvent event) {
	            Notification.show("The time is " + new Date());
	            
	            
	            Notification.show("Querying clubs");
				List<Club> clubs = DML.retrieveAllClubs();
				String clubsStr = "--clubs:\n";
				for(Club c:clubs){
					clubsStr += c.getCompactString();
				}
				System.out.println(clubsStr);
				
				List<Player> players = DML.retrieveAllPlayers();
				String plyrsStr = "--players:\n";
				for(Player p:players)
					plyrsStr += p.getCompactString();
				System.out.println(plyrsStr);
				
				List<Tournament> trnmts = DML.retrieveAllTournaments();
				System.out.println("--tournaments:");
				for (Tournament t : trnmts) {
					System.out.println(t.getCompactString());
				}
				
				List<EventResult> evnt = DML.retrieveEventsFromPlayer(20978);
				System.out.println("--events of player 20978:");
				for(EventResult e:evnt)
					System.out.println(e.getCompactString());
				
				List<OwnershipResult> own = DML.retrieveOwnershipsFromPlayer(20978);
				System.out.println("--ownerships of player 20978:");
				for(OwnershipResult o:own)
					System.out.println(o.getCompactString());
				
				MatchUpDetails mud = DML.retrieveMatchUpDetails("53-4-3");
				System.out.println("--matchupdetails of 53-4-3:");
				System.out.println(mud.getCompactString());
				
				PlayerCareerInfo pci = DML.retrieveCareerInfo(20978);
				System.out.println("--playercareerinfo di 20978:");
				System.out.println(pci.getCompactString());
				
				List<MatchUpResult> tres = DML.retrieveMatchResultsFromTournament("Campionato serie A maschile", 2013);
				System.out.println("--Campionato serie A maschile 2013:");
				for(MatchUpResult mur:tres){
					System.out.println(mur.getCompactString());
				}
				
				List<MatchDay> allmd = DML.retrieveAllMatchDays();
				System.out.println("--tutti i md:");
				for(MatchDay md:allmd){
					System.out.println(md.getCompactString());
				}
	        }
	    }));
	}
}