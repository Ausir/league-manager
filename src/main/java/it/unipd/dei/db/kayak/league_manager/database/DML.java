package it.unipd.dei.db.kayak.league_manager.database;

import it.unipd.dei.db.kayak.league_manager.data.Club;
import it.unipd.dei.db.kayak.league_manager.data.ClubDetails;
import it.unipd.dei.db.kayak.league_manager.data.EventResult;
import it.unipd.dei.db.kayak.league_manager.data.LMUser;
import it.unipd.dei.db.kayak.league_manager.data.Location;
import it.unipd.dei.db.kayak.league_manager.data.MatchDay;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;
import it.unipd.dei.db.kayak.league_manager.data.OwnershipResult;
import it.unipd.dei.db.kayak.league_manager.data.Player;
import it.unipd.dei.db.kayak.league_manager.data.PlayerCareerEvent;
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
					"c.id as club_id, " +
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
					long clubID = rs.getLong("club_id");
					ret.add(new OwnershipResult(ownershipID, playerId, clubID, clubName, start, end, borrowed));
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
//	private static PlayerMatchUpInfo retrievePlMaUpInfoFromPlayerAndMatch(long playerID, String matchUpID){
//		Connection con = null;
//		PreparedStatement pst = null;
//		ResultSet rs = null;
//		
//		PlayerMatchUpInfo ret = null;
//		
//		try {
//			con = DriverManager.getConnection(Helper.URL, Helper.USER,
//					Helper.PASSWORD);
//
//			String stm = "SELECT o.player_id, pls.match_id, o.club_id, p.name, cu.player_number, lu.id as lineup_id " +
//					"FROM lm.Ownership as o " +
//					"INNER JOIN lm.Player as p ON o.player_id=p.id, " +
//					"lm.Plays as pls " +
//					"INNER JOIN lm.LineUp as lu on (pls.lineup_host=lu.id OR pls.lineup_guest=lu.id) " +
//					"INNER JOIN lm.CallsUp as cu ON lu.id=cu.lineup_id " +
//					"WHERE o.player_id=? AND pls.match_id=? AND o.club_id=cu.club_id";
//			pst = con.prepareStatement(stm);
//			pst.setLong(1, playerID);
//			pst.setString(2, matchUpID);
//			rs = pst.executeQuery();
//			
//			if (rs.next()) {
//				long clubID = rs.getLong("club_id");
//				String lineUpId = rs.getString("lineup_id");
//				Club club = retrieveClubFromLineUp(lineUpId);
//				String clubName = club.getName();
//				String playerName = rs.getString("name");
//				int number = rs.getInt("player_number");
//				ret = new PlayerMatchUpInfo(playerID, matchUpID, clubID, playerName, clubName, number);
//			}
//			
//		} catch (SQLException ex) {
//		Logger lgr = Logger.getLogger(DML.class.getName());
//		lgr.log(Level.SEVERE, ex.getMessage(), ex);
//
//	} finally {
//
//		try {
//			if (rs != null) {
//				rs.close();
//			}
//			if (pst != null) {
//				pst.close();
//			}
//			if (con != null) {
//				con.close();
//			}
//
//			} catch (SQLException ex) {
//				Logger lgr = Logger.getLogger(DML.class.getName());
//				lgr.log(Level.SEVERE, ex.getMessage(), ex);
//			}
//		}
//		return ret;
//	}

	// inizialmente volevo spezzare questo metodo in 2 parti ma rischia di portare a inconsistenze
	public static List<EventResult> retrieveEventsFromPlayer(long playerId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<EventResult> ret = null;

		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);

			String stm = "SELECT p.name as player_name, e.id as event_id, e.match_id, a.name as action_name, e.instant, e.fraction, a.display_name, cu.player_number, c.name as club_name, c.id as club_id " +
					"FROM lm.Event as e " +
					"INNER JOIN lm.Action as a ON e.action=a.name " +
					"INNER JOIN lm.Ownership as o ON e.ownership_id=o.id " +
					"INNER JOIN lm.Player as p ON o.player_id=p.id " +
					"INNER JOIN lm.Plays as pls ON e.match_id=pls.match_id " +
					"INNER JOIN lm.LineUp as lu ON (pls.lineup_host=lu.id OR pls.lineup_guest=lu.id) " +
					"INNER JOIN lm.CallsUp as cu ON (lu.id=cu.lineup_id AND o.id=cu.ownership_id) " +
					"INNER JOIN lm.Club as c ON o.club_id=c.id " +
					"WHERE p.id=? AND lu.club_id=cu.club_id AND lu.club_id=o.club_id";

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
					long clubID = rs.getLong("club_id");
					String playerName = rs.getString("player_name");
					String clubName = rs.getString("club_name");
					int number = rs.getInt("player_number");
					PlayerMatchUpInfo playerMuInfo = new PlayerMatchUpInfo(playerId, matchUpID, clubID, playerName, clubName, number);
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
					"ORDER BY fraction ASC, instant ASC";

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
	public static List<Tournament> retrieveAllTournaments() {
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
				while (rs.next()) {
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
	
	public static MatchUpDetails retrieveMatchUpDetails(String matchUpId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		MatchUpDetails ret = null;

		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);
			String stm = "SELECT luh.id as lineup_host, " +
					"lug.id as lineup_guest, " +
					"mu.pitch_name, " +
					"mu.pitch_location as location_id, " +
					"mu.match_day, " +
					"mu.tournament_phase_name, " +
					"mu.tournament_name, " +
					"mu.tournament_phase_year, " +
					"mu.start_date, " +
					"mu.goals_host, " +
					"mu.goals_guest, " +
					"mu.start_time, " +
					"mu.lineman1, " +
					"mu.lineman2, " +
					"mu.timekeeper1, " +
					"mu.timekeeper2, " +
					"mu.scorekeeper, " +
					"mu.referee1, " +
					"mu.referee2, " +
					"l.name as loc_name, " +
					"ch.id as ch_id, " +
					"ch.name as ch_name, " +
					"cg.id as cg_id, " +
					"cg.name as cg_name " + 
					"FROM lm.Plays as p " +
					"INNER JOIN lm.MatchUp as mu ON p.match_id=mu.id " +
					"INNER JOIN lm.Location as l ON mu.pitch_location=l.id " +
					"INNER JOIN lm.LineUp as luh ON p.lineup_host=luh.id " +
					"INNER JOIN lm.LineUp as lug ON p.lineup_guest=lug.id " +
					"INNER JOIN lm.Club as ch ON luh.club_id=ch.id " +
					"INNER JOIN lm.Club as cg ON lug.club_id=cg.id " +
					"WHERE mu.id=?";
			pst = con.prepareStatement(stm);
			pst.setString(1, matchUpId);
			rs = pst.executeQuery();
			if (rs.next()) {
				String pitchName = rs.getString("pitch_name");
				long locationID = rs.getLong("location_id");
				String locationName = rs.getString("loc_name");
				String matchDayID = rs.getString("match_day");
				String tournamentPhaseName = rs.getString("tournament_phase_name");
				String tournamentName = rs.getString("tournament_name");
				long tournamentYear = rs.getLong("tournament_phase_year");
				Date date = rs.getDate("start_date");
				String luHost = rs.getString("lineup_host");
				String luGuest = rs.getString("lineup_guest");
				long clubHostID = rs.getLong("ch_id");
				long clubGuestID = rs.getLong("cg_id");
				String teamHostName = rs.getString("ch_name");
				String teamGuestName = rs.getString("cg_name");
				int teamHostGoals = rs.getInt("goals_host");
				int teamGuestGoals = rs.getInt("goals_guest");
				Time time = rs.getTime("start_time");
				String lineman1 = rs.getString("lineman1");
				String lineman2 = rs.getString("lineman2");
				String timekeeper1 = rs.getString("timekeeper1");
				String timekeeper2 = rs.getString("timekeeper2");
				String scorekeeper = rs.getString("scorekeeper");
				String referee1 = rs.getString("referee1");
				String referee2 = rs.getString("referee2");
				MatchUpResult result = new MatchUpResult(matchUpId, matchDayID, tournamentPhaseName, tournamentName, tournamentYear, clubHostID, clubGuestID, date, teamHostName, teamGuestName, teamHostGoals, teamGuestGoals, time);
				// ora faccio le query per i campi rimanenti: eventList, hostLineUp, guestLineUp
				// per ottenere le lineup passo il relativo id ma anche il nome del club e l'id del matchup che ho già calcolato in precedenza per rendere meno onerose le query
				List<PlayerMatchUpInfo> hostLineUp = retrieveLineUpDetails(luHost, matchUpId, teamHostName);
				List<PlayerMatchUpInfo> guestLineUp = retrieveLineUpDetails(luGuest, matchUpId, teamGuestName);;
				// per rendere le query più snelle potrei passare al metodo anche le lineup
				List<EventResult> eventList = retrieveEventsFromMatchUp(matchUpId);
				ret = new MatchUpDetails(luHost, luGuest, pitchName, locationID, result, locationName, eventList, hostLineUp, guestLineUp, lineman1, lineman2, timekeeper1, timekeeper2, scorekeeper, referee1, referee2);
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

	public static List<PlayerCareerEvent> retrievePlayerCareerEvents(long playerId){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<PlayerCareerEvent> ret = new ArrayList<PlayerCareerEvent>();

		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);
			String stm = "SELECT mu.tournament_name, " +
					"mu.tournament_phase_year, " +
					"mu.match_day, e.match_id, " +
					"c.id as player_club_id, " +
					"ch.id as clubh_id, " +
					"cg.id as clubg_id, " +
					"e.id as event_id, " +
					"c.name as player_club_name, " +
					"md.start_date as md_start_date, " +
					"md.end_date as md_end_date, " +
					"mu.start_date as match_date, " +
					"mu.start_time as match_time, " +
					"ch.name as clubh_name, " +
					"cg.name as clubg_name, " +
					"mu.goals_host, " +
					"mu.goals_guest, " +
					"e.instant, e.fraction, a.display_name " +
					"FROM lm.Ownership as o " +
					"INNER JOIN lm.Event as e ON o.id=e.ownership_id " +
					"INNER JOIN lm.MatchUp as mu ON e.match_id=mu.id " +
					"INNER JOIN lm.Club as c ON o.club_id=c.id " +
					"INNER JOIN lm.MatchDay as md ON mu.match_day=md.id " +
					"INNER JOIN lm.Plays as pls ON mu.id=pls.match_id " +
					"INNER JOIN lm.LineUp as luh ON pls.lineup_host=luh.id " +
					"INNER JOIN lm.LineUp as lug ON pls.lineup_guest=lug.id " +
					"INNER JOIN lm.Club as ch ON luh.club_id=ch.id " +
					"INNER JOIN lm.Club as cg ON lug.club_id=cg.id " +
					"INNER JOIN lm.Action as a ON e.action=a.display_name " +
					"WHERE o.player_id=?";
			pst = con.prepareStatement(stm);
			pst.setLong(1, playerId);
			rs = pst.executeQuery();

			while(rs.next()) {
				String tournamentName = rs.getString("tournament_name");
				int tournamentYear = rs.getInt("tournament_phase_year");
				String matchDayID = rs.getString("match_day");
				String matchUpID = rs.getString("match_id");
				long playerClubID = rs.getLong("player_club_id");
				long hostClubID = rs.getLong("clubh_id");
				long guestClubID = rs.getLong("clubg_id");
				long eventID = rs.getLong("event_id");
				String playerClubName = rs.getString("player_club_name");
				Date matchDayStartDate = rs.getDate("md_start_date");
				Date matchDayEndDate = rs.getDate("md_end_date");
				Date matchUpDate = rs.getDate("match_date");
				Time matchUpTime = rs.getTime("match_time");
				String hostClubName = rs.getString("clubh_name");
				String guestClubName = rs.getString("clubg_name");
				int hostGoals = rs.getInt("goals_host");
				int guestGoals = rs.getInt("goals_guest");
				int instant = rs.getInt("instant");
				int fraction = rs.getInt("fraction");
				String actionDisplay = rs.getString("display_name");

				ret.add(new PlayerCareerEvent(tournamentName, tournamentYear,
						matchDayID, matchUpID, playerClubID, hostClubID,
						guestClubID, eventID, playerClubName,
						matchDayStartDate, matchDayEndDate, matchUpDate,
						matchUpTime, hostClubName, guestClubName, hostGoals,
						guestGoals, instant, fraction, actionDisplay));

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

	
//	private static Club retrieveClubFromLineUp(String lineUpId){
//		Connection con = null;
//		PreparedStatement pst = null;
//		ResultSet rs = null;
//
//		Club ret = null;
//
//		try {
//			con = DriverManager.getConnection(Helper.URL, Helper.USER,
//					Helper.PASSWORD);
//			String stm = "SELECT c.id, c.name, c.short_name, c.phone_number, c.address, c.email, c.website " +
//					"FROM lm.Lineup as lu " +
//					"INNER JOIN lm.Club as c ON lu.club_id=c.id " +
//					"WHERE lu.id=?;";
//			pst = con.prepareStatement(stm);
//			pst.setString(1, lineUpId);
//			rs = pst.executeQuery();
//			if (rs.next()) {
//				long id = rs.getLong("id");
//				String name = rs.getString("name");
//				String shortName = rs.getString("short_name");
//				String phone = rs.getString("phone_number");
//				String address = rs.getString("address");
//				String email = rs.getString("email");
//				String website = rs.getString("website");
//				ret = new Club(id, name, shortName, phone, address, email, website);
//			}
//
//		} catch (SQLException ex) {
//			Logger lgr = Logger.getLogger(DML.class.getName());
//			lgr.log(Level.SEVERE, ex.getMessage(), ex);
//
//		} finally {
//
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (pst != null) {
//					pst.close();
//				}
//				if (con != null) {
//					con.close();
//				}
//
//			} catch (SQLException ex) {
//				Logger lgr = Logger.getLogger(DML.class.getName());
//				lgr.log(Level.SEVERE, ex.getMessage(), ex);
//			}
//		}
//		return ret;
//	}
	
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

	public static List<Location> retrieveAllLocations() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<Location> ret = null;
		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);

			String stm = "SELECT l.id, l.city, l.name " +
					"FROM lm.Location as l";

			pst = con.prepareStatement(stm);
			rs = pst.executeQuery();
			if (!rs.isAfterLast()) {
				ret = new ArrayList<Location>();
				while (rs.next()) {
					long id = rs.getLong("id");
					String city = rs.getString("city");
					String name = rs.getString("name");
					
					ret.add(new Location(id, city, name));
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
	
	public static List<Player> retrievePlayersFromClub(long clubId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<Player> ret = null;
		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);

			String stm = "SELECT DISTINCT p.id, p.name, p.birthday " +
					"FROM lm.Club as c " +
					"INNER JOIN lm.LineUp as lu ON c.id=lu.club_id " +
					"INNER JOIN lm.CallsUp as cu ON lu.id=cu.lineup_id " +
					"INNER JOIN lm.Ownership as o ON cu.ownership_id=o.id " +
					"INNER JOIN lm.Player as p ON o.player_id=p.id " +
					"WHERE c.id=? AND o.end_date>=current_date";

			pst = con.prepareStatement(stm);
			pst.setLong(1, clubId);
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
	
	public static List<LMUser> retrieveManagersFromClub(long clubId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<LMUser> ret = null;
		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);

			String stm = "SELECT u.user_email, u.password, u.phone_number, u.first_name, u.last_name, u.birthday " +
					"FROM lm.Coordinates as c " +
					"INNER JOIN lm.LMUser as u ON c.manager=u.user_email " +
					"WHERE c.club=?";

			pst = con.prepareStatement(stm);
			pst.setLong(1, clubId);
			rs = pst.executeQuery();
			if (!rs.isAfterLast()) {
				ret = new ArrayList<LMUser>();
				while (rs.next()) {
					String email = rs.getString("user_email");
					byte[] password = rs.getBytes("password");
					String phone = rs.getString("phone_number");
					String firstName = rs.getString("first_name");
					String lastName = rs.getString("last_name");
					Date birthday = rs.getDate("birthday");
					
					ret.add(new LMUser(email, password, phone, firstName, lastName, birthday));
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
	
	public static ClubDetails retrieveClubDetails(long clubId){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		ClubDetails ret = null;
		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);

			String stm = "SELECT c.name, c.phone_number, c.email, c.address, c.website " +
					"FROM lm.Club as c " +
					"WHERE c.id=?";

			pst = con.prepareStatement(stm);
			pst.setLong(1, clubId);
			rs = pst.executeQuery();
			if (rs.next()) {
				String name = rs.getString("name");
				String phone = rs.getString("phone_number");
				String address = rs.getString("address");
				String email = rs.getString("email");
				String website = rs.getString("website");
				List<Player> clubPlayers = retrievePlayersFromClub(clubId);
				List<LMUser> clubManagers = retrieveManagersFromClub(clubId);
				ret = new ClubDetails(clubId, name, phone, address, email,
						website, clubPlayers, clubManagers);
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
	
	public static EventResult retrieveEventResult(long eventId){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		EventResult ret = null;
		try {
			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);

			String stm = "SELECT p.id as player_id, e.match_id, c.id as club_id, p.name as player_name, c.name as club_name, cu.player_number, a.name as action_name, e.instant, e.fraction, a.display_name " +
					"FROM lm.Event as e " +
					"INNER JOIN lm.Action as a ON e.action=a.name " +
					"INNER JOIN lm.Ownership as o ON e.ownership_id=o.id " +
					"INNER JOIN lm.Player as p ON o.player_id=p.id " +
					"INNER JOIN lm.Plays as pls ON e.match_id=pls.match_id " +
					"INNER JOIN lm.LineUp as lu ON (pls.lineup_host=lu.id OR pls.lineup_guest=lu.id) " +
					"INNER JOIN lm.CallsUp as cu ON (lu.id=cu.lineup_id AND o.id=cu.ownership_id) " +
					"INNER JOIN lm.Club as c ON o.club_id=c.id " +
					"WHERE e.id=? AND lu.club_id=cu.club_id AND lu.club_id=o.club_id";

			pst = con.prepareStatement(stm);
			pst.setLong(1, eventId);
			rs = pst.executeQuery();
			if (rs.next()) {
				long playerID = rs.getLong("player_id");
				String matchUpID = rs.getString("match_id");;
				long clubID = rs.getLong("club_id");
				String playerName = rs.getString("player_name");
				String clubName = rs.getString("club_name");
				int number = rs.getInt("player_number");
				String actionName = rs.getString("action_name");
				int instant = rs.getInt("instant");
				int fraction = rs.getInt("fraction");
				String actionDescription = rs.getString("display_name");
				PlayerMatchUpInfo playerInfo = new PlayerMatchUpInfo(playerID, matchUpID, clubID, playerName, clubName, number);
				ret = new EventResult(eventId, matchUpID, actionName, playerInfo, instant, fraction, actionDescription);
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
	
	public static PlayerCareerInfo retrievePlayerCareerInfo(long playerId){

		Connection con = null;
		String stm = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Player playerData = null;

		try {

			con = DriverManager.getConnection(Helper.URL, Helper.USER,
					Helper.PASSWORD);
			stm = "SELECT p.id, p.name, p.birthday FROM lm.Player as p "
					+ "WHERE p.id=?";
			pst = con.prepareStatement(stm);
			pst.setLong(1, playerId);
			rs = pst.executeQuery();

			if (rs.next()) {
				long id = rs.getLong("id");
				String name = rs.getString("name");
				Date birthday = rs.getDate("birthday");
				playerData = new Player(id, name, birthday);
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
		List<OwnershipResult> ownerships = retrieveOwnershipsFromPlayer(playerId);
		List<PlayerCareerEvent> careerEvents = retrievePlayerCareerEvents(playerId);
		return new PlayerCareerInfo(playerData, ownerships, careerEvents);
	}
}