package it.unipd.dei.db.kayak.league_manager.database;

import it.unipd.dei.db.kayak.league_manager.data.Club;
import it.unipd.dei.db.kayak.league_manager.data.EventResult;
import it.unipd.dei.db.kayak.league_manager.data.MatchDay;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;
import it.unipd.dei.db.kayak.league_manager.data.OwnershipResult;
import it.unipd.dei.db.kayak.league_manager.data.Player;
import it.unipd.dei.db.kayak.league_manager.data.PlayerCareerInfo;
import it.unipd.dei.db.kayak.league_manager.data.PlayerMatchUpInfo;
import it.unipd.dei.db.kayak.league_manager.data.Tournament;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DML {

//	public static void populateDataBase() {
//		DDL.executeSQLFile("SQL_Pop_LMUser.sql");
//		DDL.executeSQLFile("SQL_Pop_Secretary.sql");		
//		DDL.executeSQLFile("SQL_Pop_Manager.sql");
//		DDL.executeSQLFile("SQL_Pop_ClubManager.sql");
//		DDL.executeSQLFile("SQL_Pop_Tournament.sql");
//		DDL.executeSQLFile("SQL_Pop_Location.sql");
//		DDL.executeSQLFile("SQL_Pop_Pitch.sql");
//		DDL.executeSQLFile("SQL_Pop_Club.sql");
//		DDL.executeSQLFile("SQL_Pop_TournamentPhase.sql");
//		DDL.executeSQLFile("SQL_Pop_MatchDay.sql");
//		DDL.executeSQLFile("SQL_Pop_MatchUp.sql");
//		DDL.executeSQLFile("SQL_Pop_LineUp.sql");
//		DDL.executeSQLFile("SQL_Pop_Plays.sql");
//		DDL.executeSQLFile("SQL_Pop_Player.sql");
//		DDL.executeSQLFile("SQL_Pop_Ownership.sql");
//		DDL.executeSQLFile("SQL_Pop_CallsUp.sql");
//		DDL.executeSQLFile("SQL_Pop_Coordinates.sql");
//		DDL.executeSQLFile("SQL_Pop_Subscription.sql");
//		DDL.executeSQLFile("SQL_Pop_Action.sql");
//		DDL.executeSQLFile("SQL_Pop_Event.sql");
//	}

	public static List<Club> retrieveAllClubs() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Club> ret = null;

		try {

			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);
			pst = con.prepareStatement("SELECT c.id, c.name, c.short_name, c.phone_number, c.address, c.email, c.website " +
					"FROM lm.Club as c " +
					"ORDER BY c.id ASC");
			rs = pst.executeQuery();

			if (!rs.isAfterLast()) {
				ret = new ArrayList<Club>();
				while (rs.next()) {
					long id = rs.getLong("id");
					String name = rs.getString("name");
					String shortName = rs.getString("short_name");
					String phone = rs.getString("phone_number");
					String address = rs.getString("address");
					String email = rs.getString("email");
					String website = rs.getString("website");
					ret.add(new Club(id, name, shortName, phone, address, email, website));
				}
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DDL.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DDL.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
		return ret;
	}

	public static List<Player> retrieveAllPlayers() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Player> ret = null;

		try {

			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);
			pst = con.prepareStatement("SELECT p.id, p.name, p.birthday " +
					"FROM lm.Player as p " +
					"ORDER BY p.id ASC");
			rs = pst.executeQuery();

			if (!rs.isAfterLast()) {
				ret = new ArrayList<Player>();
				while (rs.next()) {
					long id = rs.getLong("id");
					String name = rs.getString("name");
					Date birthday = rs.getDate("birthday");
					ret.add(new Player(id, name, birthday));
				}
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DDL.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DDL.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
		return ret;
	}
	
	public static List<OwnershipResult> retrieveOwnershipsFromPlayer(
			long playerId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<OwnershipResult> ret = null;

		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);

			String stm = "SELECT o.id as ownership_id, " +
					"c.name as club_name, " +
					"o.start_date, " +
					"o.end_date, " +
					"o.isborrowed " + 
					"FROM lm.Ownership as o " + 
					"INNER JOIN lm.Club as c ON o.club_id=c.id " + 
					"WHERE o.player_id=?";

			pst = con.prepareStatement(stm);
			pst.setLong(1, playerId);
			rs = pst.executeQuery();
			if (!rs.isAfterLast()) {
				ret = new ArrayList<OwnershipResult>();
				while (rs.next()) {
					long ownershipID = rs.getLong("ownership_id");
					String clubName = rs.getString("club_name");
					Date start = rs.getDate("start_date");
					Date end = rs.getDate("end_date");
					Boolean borrowed = rs.getBoolean("isborrowed");
					ret.add(new OwnershipResult(ownershipID, playerId, clubName, start, end, borrowed));
				}
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
		return ret;
	}
	
	/**
	 * è onere del programmatore inserire il matchupid corretto!!
	 * @param eventID
	 * @param matchUpID
	 */
	private static PlayerMatchUpInfo retrievePlMaUpInfoFromPlayerAndMatch(long playerID, String matchUpID){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		
		PlayerMatchUpInfo ret = null;
		
		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);

			String stm = "SELECT o.player_id, pls.match_id, o.club_id, p.name, cu.player_number, lu.id as lineup_id " +
					"FROM lm.Ownership as o " +
					"INNER JOIN lm.Player as p ON o.player_id=p.id, " +
					"lm.Plays as pls " +
					"INNER JOIN lm.LineUp as lu on (pls.lineup_host=lu.id OR pls.lineup_guest=lu.id) " +
					"INNER JOIN lm.CallsUp as cu ON lu.id=cu.lineup_id " +
					"WHERE o.player_id=? AND pls.match_id=? AND o.club_id=cu.club_id";
			pst = con.prepareStatement(stm);
			pst.setLong(1, playerID);
			pst.setString(2, matchUpID);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				long clubID = rs.getLong("club_id");
				String lineUpId = rs.getString("lineup_id");
				Club club = retrieveClubFromLineUp(lineUpId);
				String clubName = club.getName();
				String playerName = rs.getString("name");
				int number = rs.getInt("player_number");
				ret = new PlayerMatchUpInfo(playerID, matchUpID, clubID, playerName, clubName, number);
			}
			
		} catch (SQLException ex) {
		Logger lgr = Logger.getLogger(DML.class.getName());
		lgr.log(Level.SEVERE, ex.getMessage(), ex);

	} finally {

		try {
			if (rs != null) {
				rs.close();
			}
			if (pst != null) {
				pst.close();
			}
			if (con != null) {
				con.close();
			}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
		return ret;
	}

	// inizialmente volevo spezzare questo metodo in 2 parti ma rischia di portare a inconsistenze
	public static List<EventResult> retrieveEventsFromPlayer(long playerId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<EventResult> ret = null;

		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);

			String stm = "SELECT e.id as event_id, e.match_id, a.name as action_name, e.instant, e.fraction, a.display_name, e.match_id " +
					"FROM lm.Event as e " +
					"INNER JOIN lm.Action as a ON e.action=a.name " +
					"INNER JOIN lm.Ownership as o ON e.ownership_id=o.id " +
					"INNER JOIN lm.Player as p ON o.player_id=p.id " +
					"WHERE p.id=?";

			pst = con.prepareStatement(stm);
			pst.setLong(1, playerId);
			rs = pst.executeQuery();
			if (!rs.isAfterLast()) {
				ret = new ArrayList<EventResult>();
				while (rs.next()) {
					String matchUpID = rs.getString("match_id");
					long eventID = rs.getLong("event_id");
					String actionName = rs.getString("action_name");
					int instant = rs.getInt("instant");
					int fraction = rs.getInt("fraction");
					String actionDescription = rs.getString("display_name");
					PlayerMatchUpInfo playerMuInfo = retrievePlMaUpInfoFromPlayerAndMatch(playerId, matchUpID);
					ret.add(new EventResult(eventID, matchUpID, actionName, playerMuInfo, instant, fraction, actionDescription));
				}
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
		return ret;
	}

	public static List<EventResult> retrieveEventsFromMatchUp(String matchUpId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<EventResult> ret = null;

		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);

			String stm = "SELECT e.id as event_id, e.match_id, a.name as action_name, p.id as player_id, o.club_id, p.name, cu.player_number, e.instant, e.fraction, a.display_name, c.name as club_name " +
					"FROM lm.event as e " +
					"INNER JOIN lm.Plays as pls ON e.match_id=pls.match_id " +
					"INNER JOIN lm.LineUp as lu ON pls.lineup_host=lu.id OR pls.lineup_guest=lu.id " +
					"INNER JOIN lm.Ownership as o ON e.ownership_id=o.id " +
					"INNER JOIN lm.Player as p ON o.player_id=p.id " +
					"INNER JOIN lm.CallsUp as cu ON lu.id=cu.lineup_id AND o.id=cu.ownership_id " +
					"INNER JOIN lm.action as a ON a.name=e.action " +
					"INNER JOIN lm.Club as c ON o.club_id=c.id " + 
					"WHERE e.match_id=? AND lu.club_id=o.club_id " +
					"ORDER BY fraction ASC, instant ASC;";

			pst = con.prepareStatement(stm);
			pst.setString(1, matchUpId);
			rs = pst.executeQuery();
			if (!rs.isAfterLast()) {
				ret = new ArrayList<EventResult>();
				while (rs.next()) {
					long eventID = rs.getLong("event_id");
					String matchUpID = rs.getString("match_id");
					String actionName = rs.getString("action_name");
					
					long playerID = rs.getLong("player_id");
					long clubID = rs.getLong("club_id");
					String playerName = rs.getString("name");
					String clubName = rs.getString("club_name");
					int number = rs.getInt("player_number");
					
					int instant = rs.getInt("instant");
					int fraction = rs.getInt("fraction");
					String actionDescription = rs.getString("display_name");
					ret.add(new EventResult(eventID, matchUpID, actionName, new PlayerMatchUpInfo(playerID, matchUpID, clubID, playerName, clubName, number), instant, fraction, actionDescription));
				}
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
		return ret;
	}

	/**
	 * metodo di supporto a retrieveMatchUpDetails
	 * richiede matchUpId e clubName per rendere più snelle le query risparmiando un inner join
	 * @return
	 */
	private static List<PlayerMatchUpInfo> retrieveLineUpDetails(String lineUpId, String matchUpId, String clubName){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<PlayerMatchUpInfo> ret = null;
		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);

			String stm = "SELECT o.player_id, o.club_id, p.name, cu.player_number " +
					"FROM lm.LineUp as lu " +
					"INNER JOIN lm.CallsUp as cu ON lu.id=cu.lineup_id " +
					"INNER JOIN lm.Ownership as o ON cu.ownership_id=o.id " +
					"INNER JOIN lm.Player as p ON o.player_id=p.id " +
					"WHERE lu.id=?";

			pst = con.prepareStatement(stm);
			pst.setString(1, lineUpId);
			rs = pst.executeQuery();
			if (!rs.isAfterLast()) {
				ret = new ArrayList<PlayerMatchUpInfo>();
				while (rs.next()) {
					long playerID = rs.getLong("player_id");
					long clubID = rs.getLong("club_id");
					String playerName = rs.getString("name");
					int number = rs.getInt("player_number");
					ret.add(new PlayerMatchUpInfo(playerID, matchUpId, clubID, playerName, clubName, number));
				}
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
		return ret;
	}
	
	/**
	 * ritorna i più recenti tornei. se viene inserito un numero negativo li restituisce tutti
	 * @param numberOfTournaments
	 * @return
	 */
	public static List<Tournament> retrieveMostRecentTournaments(int numberOfTournaments) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<Tournament> ret = null;
		/*
		 * private String name; private int year; private int maxAge; private
		 * boolean sex; private String organizerEmail;
		 */
		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);

			String stm = "SELECT name, year, max_age, sex, organizer "
					+ "FROM lm.Tournament";

			pst = con.prepareStatement(stm);
			rs = pst.executeQuery();
			if (!rs.isAfterLast()) {
				ret = new ArrayList<Tournament>();
				while (rs.next() && numberOfTournaments-- != 0) {
					String name = rs.getString("name");
					int year = rs.getInt("year");
					int maxAge = rs.getInt("max_age");
					boolean sex = rs.getBoolean("sex");
					String organizerEmail = rs.getString("organizer");
					ret.add(new Tournament(name, year, maxAge, sex, organizerEmail));
				}
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
		return ret;
	}
	
	public static List<Tournament> retrieveAllTournaments() {
		return retrieveMostRecentTournaments(-1);
	}

	public static MatchUpDetails retrieveMatchUpDetails(String matchUpId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		MatchUpDetails ret = null;

		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);
			String stm = "SELECT p.lineup_host, "
					+ "p.lineup_guest, "
					+ "mu.pitch_name, "
					+ "mu.pitch_location as location_id, "
					+ "mu.match_day, "
					+ "mu.tournament_phase_name, "
					+ "mu.tournament_name, "
					+ "mu.tournament_phase_year, "
					+ "mu.start_date, "
					+ "mu.goals_host, "
					+ "mu.goals_guest, "
					+ "mu.start_time, "
					+ "l.name as loc_name "
					+ "FROM lm.Plays as p "
					+ "INNER JOIN lm.MatchUp as mu ON p.match_id=mu.id "
					+ "INNER JOIN lm.Location as l ON mu.pitch_location=l.id "
					+ "WHERE mu.id=?";
			pst = con.prepareStatement(stm);
			pst.setString(1, matchUpId);
			rs = pst.executeQuery();
			if (rs.next()) {
				String luHost = rs.getString("lineup_host");
				String luGuest = rs.getString("lineup_guest");
				String pitchName = rs.getString("pitch_name");
				long locationID = rs.getLong("location_id");
				String locationName = rs.getString("loc_name");
				String matchDayID = rs.getString("match_day");
				String tournamentPhaseName = rs.getString("tournament_phase_name");
				String tournamentName = rs.getString("tournament_name");
				long tournamentYear = rs.getLong("tournament_phase_year");
				Date date = rs.getDate("start_date");
				Club hostClub = retrieveClubFromLineUp(luHost);
				Club guestClub = retrieveClubFromLineUp(luGuest);
				long clubHostID = hostClub.getId();
				long clubGuestID = guestClub.getId();
				String teamHostName = hostClub.getName();
				String teamGuestName = guestClub.getName();
				int teamHostGoals = rs.getInt("goals_host");
				int teamGuestGoals = rs.getInt("goals_guest");
				Time time = rs.getTime("start_time");
				MatchUpResult result = new MatchUpResult(matchUpId, matchDayID, tournamentPhaseName, tournamentName, tournamentYear, clubHostID, clubGuestID, date, teamHostName, teamGuestName, teamHostGoals, teamGuestGoals, time);
				// ora faccio le query per i campi rimanenti: eventList, hostLineUp, guestLineUp
				// per ottenere le lineup passo il relativo id ma anche il nome del club e l'id del matchup che ho già calcolato in precedenza per rendere meno onerose le query
				List<PlayerMatchUpInfo> hostLineUp = retrieveLineUpDetails(luHost, matchUpId, teamHostName);
				List<PlayerMatchUpInfo> guestLineUp = retrieveLineUpDetails(luGuest, matchUpId, teamGuestName);;
				// per rendere le query più snelle potrei passare al metodo anche le lineup
				List<EventResult> eventList = retrieveEventsFromMatchUp(matchUpId);
				ret = new MatchUpDetails(luHost, luGuest, pitchName, locationID, result, locationName, eventList, hostLineUp, guestLineUp);
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
		return ret;
	}

	public static PlayerCareerInfo retrieveCareerInfo(long playerId){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		PlayerCareerInfo ret = null;

		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);
			String stm = "SELECT p.name "
					+ "FROM lm.Player as p "
					+ "WHERE p.id=?";
			pst = con.prepareStatement(stm);
			pst.setLong(1, playerId);
			rs = pst.executeQuery();
			if (rs.next()) {
				String playerName = rs.getString("name");
				List<OwnershipResult> ownerships = retrieveOwnershipsFromPlayer(playerId);
				List<EventResult> events = retrieveEventsFromPlayer(playerId);
				ret = new PlayerCareerInfo(playerId, playerName, events, ownerships);
				
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
		return ret;
	}

	
	private static Club retrieveClubFromLineUp(String lineUpId){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		Club ret = null;

		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);
			String stm = "SELECT c.id, c.name, c.short_name, c.phone_number, c.address, c.email, c.website " +
					"FROM lm.Lineup as lu " +
					"INNER JOIN lm.Club as c ON lu.club_id=c.id " +
					"WHERE lu.id=?;";
			pst = con.prepareStatement(stm);
			pst.setString(1, lineUpId);
			rs = pst.executeQuery();
			if (rs.next()) {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				String shortName = rs.getString("short_name");
				String phone = rs.getString("phone_number");
				String address = rs.getString("address");
				String email = rs.getString("email");
				String website = rs.getString("website");
				ret = new Club(id, name, shortName, phone, address, email, website);
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
		return ret;
	}
	
	public static List<MatchUpResult> retrieveMatchResultsFromTournament(String tournamentName, int tournamentYear){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<MatchUpResult> ret = null;
		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);

			String stm = "SELECT p.match_id, mu.match_day, tp.name as t_phase_name, mu.start_date, mu.start_time, mu.goals_host, mu.goals_guest, chost.id as host_id, chost.name as host_name, cguest.id as guest_id, cguest.name as guest_name " +
					"FROM lm.Tournament as t " +
					"INNER JOIN lm.TournamentPhase as tp ON t.name=tp.tournament_name AND t.year=tp.tournament_year " +
					"INNER JOIN lm.MatchDay as md ON t.name=md.tournament_name AND t.year=md.tournament_year " +
					"INNER JOIN lm.MatchUp as mu ON md.id=mu.match_day " +
					"INNER JOIN lm.Plays as p ON mu.id=p.match_id " +
					"INNER JOIN lm.LineUp as lhost ON p.lineup_host=lhost.id " +
					"INNER JOIN lm.Club as chost ON lhost.club_id=chost.id " + 
					"INNER JOIN lm.LineUp as lguest ON p.lineup_guest=lguest.id " +
					"INNER JOIN lm.Club as cguest ON lguest.club_id=cguest.id " + 
					"WHERE t.name=? AND t.year=?";

			pst = con.prepareStatement(stm);
			pst.setString(1, tournamentName);
			pst.setInt(2, tournamentYear);
			System.out.println("executing query");
			rs = pst.executeQuery();
			if (!rs.isAfterLast()) {
				System.out.println("analyzing first one");
				ret = new ArrayList<MatchUpResult>();
				while (rs.next()) {
					String matchUpID = rs.getString("match_id");
					String matchDayID = rs.getString("match_day");
					String tournamentPhaseName = rs.getString("t_phase_name");
					Date date = rs.getDate("start_date");
					int teamHostGoals = rs.getInt("goals_host");
					int teamGuestGoals = rs.getInt("goals_guest");
					Time time = rs.getTime("start_time");
//					String lineUpHost = rs.getString("lineup_host");
//					String lineUpGuest = rs.getString("lineup_guest");
//					Club host = retrieveClubFromLineUp(lineUpHost);
//					Club guest = retrieveClubFromLineUp(lineUpGuest);
					long clubHostID = rs.getLong("host_id");
					String teamHostName = rs.getString("host_name");
					long clubGuestID = rs.getLong("guest_id");
					String teamGuestName = rs.getString("guest_name");
					ret.add(new MatchUpResult(matchUpID, matchDayID, tournamentPhaseName, tournamentName, tournamentYear, clubHostID, clubGuestID, date, teamHostName, teamGuestName, teamHostGoals, teamGuestGoals, time));
					System.out.println("added");
				}
			}
			else{
				System.out.println("trovato niente");
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
		return ret;
	}
	
	public static List<MatchDay> retrieveAllMatchDays() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<MatchDay> ret = null;
		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);

			String stm = "SELECT md.id, md.num, md.start_date, md.end_date, md.club, md.location, md.tournament_name, md.tournament_year " +
					"FROM lm.MatchDay as md";

			pst = con.prepareStatement(stm);
			rs = pst.executeQuery();
			if (!rs.isAfterLast()) {
				ret = new ArrayList<MatchDay>();
				while (rs.next()) {
					String id = rs.getString("id");
					int num = rs.getInt("num");
					Date startDate = rs.getDate("start_date");
					Date endDate = rs.getDate("end_date");
					long clubID = rs.getLong("club");
					String tournamentName = rs.getString("tournament_name");
					int tournamentYear = rs.getInt("tournament_year");
					long locationID = rs.getLong("location");
					
					ret.add(new MatchDay(id, num, startDate, endDate, clubID, locationID, tournamentName, tournamentYear));
				}
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.SEVERE, ex.getMessage(), ex);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
				if (con != null) {
					con.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.SEVERE, ex.getMessage(), ex);
			}
		}
		return ret;
	}
}