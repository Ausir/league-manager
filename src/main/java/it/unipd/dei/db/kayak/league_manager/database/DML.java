package it.unipd.dei.db.kayak.league_manager.database;

import it.unipd.dei.db.kayak.league_manager.data.Club;
import it.unipd.dei.db.kayak.league_manager.data.ClubDetails;
import it.unipd.dei.db.kayak.league_manager.data.EventResult;
import it.unipd.dei.db.kayak.league_manager.data.LMUser;
import it.unipd.dei.db.kayak.league_manager.data.LMUserDetails;
import it.unipd.dei.db.kayak.league_manager.data.Location;
import it.unipd.dei.db.kayak.league_manager.data.MatchDay;
import it.unipd.dei.db.kayak.league_manager.data.MatchDayDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchDayMatches;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpDetails;
import it.unipd.dei.db.kayak.league_manager.data.MatchUpResult;
import it.unipd.dei.db.kayak.league_manager.data.Ownership;
import it.unipd.dei.db.kayak.league_manager.data.OwnershipResult;
import it.unipd.dei.db.kayak.league_manager.data.Player;
import it.unipd.dei.db.kayak.league_manager.data.PlayerCareerEvent;
import it.unipd.dei.db.kayak.league_manager.data.PlayerCareerInfo;
import it.unipd.dei.db.kayak.league_manager.data.PlayerMatchUpInfo;
import it.unipd.dei.db.kayak.league_manager.data.Tournament;
import it.unipd.dei.db.kayak.league_manager.data.TournamentDetails;
import it.unipd.dei.db.kayak.league_manager.data.TournamentEssentials;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.vaadin.ui.Notification;

public class DML {
	
	/**
	 * Inserts the specified player into the database, if the id is not yet in use.
	 * @param player the object containing the player info
	 * @return true if the insertion was successful, false otherwise
	 */
	public static boolean addPlayer(Player player) {
		// just check
		if (player == null) {
			return false;
		}
		
		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = Helper.getConnection();
			String stm = "INSERT INTO lm.Player VALUES (?, ?, ?);";
			pst = con.prepareStatement(stm);
			pst.setLong(1, player.getID());
			pst.setString(2, player.getName());
			pst.setDate(3, player.getBirthday());

			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			return false;
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Adds a new owenrship to the database.
	 * @param ownership the ownership data to be inserted
	 * @return true if the insertion was successful, false otherwise
	 */
	public static boolean addOwnership(Ownership ownership) {
		// just check
		if (ownership == null) {
			return false;
		}

		Connection con = null;
		PreparedStatement pst = null;

		try {
			con = Helper.getConnection();
			String stm = "INSERT INTO lm.Ownership (player_id, club_id, isborrowed, start_date,  end_date) "
					+ "VALUES (?, ?, ?, ?, ?);";
			pst = con.prepareStatement(stm);
			pst.setLong(1, ownership.getPlayerID());
			pst.setLong(2, ownership.getClubID());
			pst.setBoolean(3, ownership.isBorrowed());
			pst.setDate(4, ownership.getStartDate());
			pst.setDate(5, ownership.getStartDate());

			pst.executeUpdate();
		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			return false;
		} finally {
			try {
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Updates the password for the specified user.
	 * @param userEmail the user email
	 * @param password the new password as sha32
	 * @return true if the update was successful, false otherwise
	 */
	public static boolean setLMUserPassword(String userEmail, byte[] password) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		if (password.length != 32) {
			System.out.println("Wrong password length, should be 32; supplied "
					+ password.length);
			return false;
		}

		try {
			con = Helper.getConnection();
			String stm = "UPDATE lm.LMUser SET password = ? WHERE user_email = ?;";
			pst = con.prepareStatement(stm);
			pst.setString(1, new String(password, "UTF-8"));
			pst.setString(2, userEmail);
			rs = pst.executeQuery();
		} catch (Exception ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			return false;
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
				return false;
			}
		}
		return true;
	}

	/**
	 * Retrieves the details of a user given its email (id).
	 * @param userEmail the email of the user to be retrieved
	 * @return LMUserDetails if the retrieve process was succesful, null otherwise
	 */
	public static LMUserDetails retrieveLMUserDetails(String userEmail) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		LMUserDetails ret = null;

		try {

			con = Helper.getConnection();
			String stm = "SELECT u.user_email AS email, u.password AS password, u.phone_number AS phone, u.first_name AS first_name, "
					+ "u.last_name AS last_name, u.birthday AS birthday, s.user_email AS s_email, "
					+ "m.user_email AS m_email, c.id AS club_id, c.short_name AS club_name "
					+ "FROM lm.LMUser AS u "
					+ "LEFT JOIN lm.Secretary AS s ON u.user_email = s.user_email "
					+ "LEFT JOIN lm.Manager AS m ON u.user_email = m.user_email "
					+ "LEFT JOIN lm.Coordinates AS coo ON u.user_email = coo.manager "
					+ "LEFT JOIN lm.Club AS c ON coo.club = c.id "
					+ "WHERE u.user_email = ?;";
			pst = con.prepareStatement(stm);
			pst.setString(1, userEmail);
			rs = pst.executeQuery();

			if (rs.next()) {
				String email = rs.getString("email");
				byte[] password = rs.getString("password").getBytes();
				String phone = rs.getString("phone");
				String firstName = rs.getString("first_name");

				String lastName = rs.getString("last_name");
				Date birthday = rs.getDate("birthday");
				boolean secretary = rs.getString("s_email") != null;
				boolean manager = rs.getString("m_email") != null;

				// correcting db's problems
				long managedClubID = rs.getLong("club_id");
				if (managedClubID == 0) {
					managedClubID = -1;
				}
				String managedClubName = rs.getString("club_name");
				if (managedClubName == null) {
					managedClubName = "";
				}

				LMUser user = new LMUser(email, password, phone, firstName,
						lastName, birthday);
				ret = new LMUserDetails(user, secretary, manager,
						managedClubID, managedClubName);
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}

	/**
	 * Retrieves the complete list of clubs.
	 * @return a list of Club objects or null if problems occurred
	 */
	public static List<Club> retrieveAllClubs() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Club> ret = null;

		try {
			con = Helper.getConnection();
			pst = con
					.prepareStatement("SELECT DISTINCT c.id, c.name, c.short_name, c.phone_number, c.address, c.email, c.website "
							+ "FROM lm.callsup AS l INNER JOIN lm.club AS c ON c.id = l.club_id "
							+ "ORDER BY c.id ASC;");
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
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}

	/**
	 * Retrieves the complete list of players that ever played or where scheduled to play in a match.
	 * @return a list of Player objects or null if a problem occurred
	 */
	public static List<Player> retrieveAllPlayers() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		List<Player> ret = null;

		try {

			con = Helper.getConnection();
			// gets all players that were sometimes included in a line-up
			pst = con.prepareStatement("SELECT DISTINCT o.player_id, p.name, p.birthday, p.id " +
					"FROM lm.callsup AS c INNER JOIN lm.ownership AS o ON c.ownership_id = o.id " +
					"INNER JOIN lm.player AS p on p.id = o.player_id " +
					"ORDER BY p.name ASC;");
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
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}
	
	/**
	 * Retrieve the list of clubs that owned a specific player, in the form of Ownership objects.
	 * @param playerId the id of the player
	 * @return a list of OwnershipResult objects or null if a problem occurred
	 */
	public static List<OwnershipResult> retrieveOwnershipsFromPlayer(
			long playerId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<OwnershipResult> ret = null;

		try {
			con = Helper.getConnection();

			String stm = "SELECT o.id AS ownership_id, " +
					"c.id AS club_id, " +
					"c.name AS club_name, " +
					"o.start_date, " +
					"o.end_date, " +
					"o.isborrowed " + 
					"FROM lm.Ownership AS o " + 
					"INNER JOIN lm.Club AS c ON o.club_id=c.id " + 
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
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}

	/**
	 * Retrieves a list of actions played by a specific player.
	 * @param playerId the id of the player whose actions have to be retrieved
	 * @return a list of EventResult objects or null if problems occurred
	 */
	public static List<EventResult> retrieveEventsFromPlayer(long playerId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<EventResult> ret = null;

		try {
			con = Helper.getConnection();

			String stm = "SELECT p.name AS player_name, e.id AS event_id, e.match_id, a.name AS action_name, e.instant, e.fraction, a.display_name, cu.player_number, c.name AS club_name, c.id AS club_id " +
					"FROM lm.Event AS e " +
					"INNER JOIN lm.Action AS a ON e.action=a.name " +
					"INNER JOIN lm.Ownership AS o ON e.ownership_id=o.id " +
					"INNER JOIN lm.Player AS p ON o.player_id=p.id " +
					"INNER JOIN lm.Plays AS pls ON e.match_id=pls.match_id " +
					"INNER JOIN lm.LineUp AS lu ON (pls.lineup_host=lu.id OR pls.lineup_guest=lu.id) " +
					"INNER JOIN lm.CallsUp AS cu ON (lu.id=cu.lineup_id AND o.id=cu.ownership_id) " +
					"INNER JOIN lm.Club AS c ON o.club_id=c.id " +
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
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}

	/**
	 * Retrieves the list of actions played in a specific match.
	 * @param matchUpId the id of the requires match
	 * @return a list of EventResult or null if problems occurred
	 */
	public static List<EventResult> retrieveEventsFromMatchUp(String matchUpId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<EventResult> ret = null;

		try {
			con = Helper.getConnection();

			String stm = "SELECT e.id AS event_id, e.match_id, a.name AS action_name, p.id AS player_id, o.club_id, p.name, cu.player_number, e.instant, e.fraction, a.display_name, c.name as club_name " +
					"FROM lm.event AS e " +
					"INNER JOIN lm.Plays AS pls ON e.match_id=pls.match_id " +
					"INNER JOIN lm.LineUp AS lu ON pls.lineup_host=lu.id OR pls.lineup_guest=lu.id " +
					"INNER JOIN lm.Ownership AS o ON e.ownership_id=o.id " +
					"INNER JOIN lm.Player AS p ON o.player_id=p.id " +
					"INNER JOIN lm.CallsUp AS cu ON lu.id=cu.lineup_id AND o.id=cu.ownership_id " +
					"INNER JOIN lm.action AS a ON a.name=e.action " +
					"INNER JOIN lm.Club AS c ON o.club_id=c.id " + 
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
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}

	/**
	 * Support method for retrieveMatchUpDetails
	 * richiede matchUpId e clubName per rendere più snelle le query risparmiando un inner join
	 * @return
	 */
	private static List<PlayerMatchUpInfo> retrieveLineUpDetails(String lineUpId, String matchUpId, String clubName){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<PlayerMatchUpInfo> ret = null;
		try {
			
			con = Helper.getConnection();

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
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}
	
	/**
	 * Returns all the tournaments in the system.
	 * @return a list of tournament objects or null if a problem occurrs
	 */
	public static List<Tournament> retrieveAllTournaments() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<Tournament> ret = null;
		try {
			con = Helper.getConnection();

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
					String sex = rs.getString("sex");
					String organizerEmail = rs.getString("organizer");
					ret.add(new Tournament(name, year, maxAge, sex, organizerEmail));
				}
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}
	
	/**
	 * Retrieve all the details of a match.
	 * @param matchUpId the id of the specified match
	 * @return a MatchUpDetails object containing the requested information
	 */
	public static MatchUpDetails retrieveMatchUpDetails(String matchUpId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		MatchUpDetails ret = null;

		try {
			con = Helper.getConnection();
			String stm = "SELECT luh.id AS lineup_host, " +
					"lug.id AS lineup_guest, " +
					"mu.pitch_name, " +
					"mu.pitch_location AS location_id, " +
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
					"md.start_date as md_start_date, " +
					"md.num, " +
					"l.name AS loc_name, " +
					"ch.id AS ch_id, " +
					"ch.name AS ch_name, " +
					"cg.id AS cg_id, " +
					"cg.name AS cg_name, " +
					"tp.num " + 
					"FROM lm.Plays AS p " +
					"INNER JOIN lm.MatchUp AS mu ON p.match_id=mu.id " +
					"INNER JOIN lm.MatchDay AS md ON md.id = mu.match_day " +
					"INNER JOIN lm.Location AS l ON mu.pitch_location=l.id " +
					"INNER JOIN lm.LineUp AS luh ON p.lineup_host=luh.id " +
					"INNER JOIN lm.LineUp AS lug ON p.lineup_guest=lug.id " +
					"INNER JOIN lm.Club AS ch ON luh.club_id=ch.id " +
					"INNER JOIN lm.Club AS cg ON lug.club_id=cg.id " +
					"INNER JOIN lm.tournamentphase AS tp ON tp.name = mu.tournament_phase_name AND tp.tournament_name = mu.tournament_name AND tp.tournament_year = mu.tournament_phase_year " +
					"WHERE mu.id=?";
			pst = con.prepareStatement(stm);
			pst.setString(1, matchUpId);
			rs = pst.executeQuery();
			if (rs.next()) {
				String pitchName = rs.getString("pitch_name");
				long locationID = rs.getLong("location_id");
				String locationName = rs.getString("loc_name");
				String matchDayID = rs.getString("match_day");
				Date matchDayDate = rs.getDate("md_start_date");
				int matchDayNum = rs.getInt("num");
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
				int tournamentPhaseNum = rs.getInt("num");
				MatchUpResult result = new MatchUpResult(matchUpId, matchDayID, matchDayDate, matchDayNum, tournamentPhaseName, tournamentPhaseNum, tournamentName, tournamentYear, clubHostID, clubGuestID, date, teamHostName, teamGuestName, teamHostGoals, teamGuestGoals, time);
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
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}

	/**
	 * Retrieve the matches of a specific match day.
	 * @param md_id the match day id
	 * @return a MatchDayMatches object containing the required information or null if problems occurred
	 */
	public static MatchDayMatches retrieveMatches(String md_id){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<MatchUpResult> results = null;
		MatchDayMatches ret = null;

		try {
			con = Helper.getConnection();

			String stm = "SELECT tp.num, mu.id as match_id, mu.match_day, md.start_date as md_start_date, md.num as md_num, mu.tournament_phase_name AS t_phase_name, mu.tournament_name, mu.tournament_phase_year , mu.start_date, mu.start_time, mu.goals_host, mu.goals_guest, ch.id as host_id, ch.name as host_name, cg.id as guest_id, cg.name as guest_name " +
					"FROM lm.matchup AS mu " +
					"INNER JOIN lm.plays AS p ON p.match_id = mu.id " +
					"INNER JOIN lm.tournamentphase AS tp ON tp.name = mu.tournament_phase_name and tp.tournament_name = mu.tournament_name and mu.tournament_phase_year = tp.tournament_year " +
					"INNER JOIN lm.lineup AS luh ON luh.id = p.lineup_host " +
					"INNER JOIN lm.lineup AS lug ON lug.id = p.lineup_guest " +
					"INNER JOIN lm.club AS ch ON ch.id = luh.club_id " +
					"INNER JOIN lm.club AS cg ON cg.id = lug.club_id " +
					"INNER JOIN lm.matchday AS md ON md.id = mu.match_day " +
					"WHERE md.id = ? " +
					"ORDER BY mu.start_date, mu.start_time ASC;";

			pst = con.prepareStatement(stm);
			pst.setString(1, md_id);
			rs = pst.executeQuery();
			
			if (!rs.isAfterLast()) {
				results = new ArrayList<MatchUpResult>();
				while (rs.next()) {
					String matchUpID = rs.getString("match_id");
					String matchDayID = rs.getString("match_day");
					Date matchDayDate = rs.getDate("md_start_date");
					int matchDayNum = rs.getInt("md_num");
					String tournamentPhaseName = rs.getString("t_phase_name");
					int tournamentPhaseNum = rs.getInt("num");
					Date date = rs.getDate("start_date");
					int teamHostGoals = rs.getInt("goals_host");
					int teamGuestGoals = rs.getInt("goals_guest");
					Time time = rs.getTime("start_time");
					long clubHostID = rs.getLong("host_id");
					String teamHostName = rs.getString("host_name");
					long clubGuestID = rs.getLong("guest_id");
					String teamGuestName = rs.getString("guest_name");
					String tournamentName = rs.getString("tournament_name");
					int tournamentYear = rs.getInt("tournament_phase_year");

					results.add(new MatchUpResult(matchUpID, matchDayID, matchDayDate, matchDayNum, tournamentPhaseName, tournamentPhaseNum, tournamentName, tournamentYear, clubHostID, clubGuestID, date, teamHostName, teamGuestName, teamHostGoals, teamGuestGoals, time));
				}
				ret = new MatchDayMatches(retrieveMatchDay(md_id), results);
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}
	
	/**
	 * Retrieves the details of a specific match day
	 * @param md_id the id of the requested match day
	 * @return a MatchDayDetails object containing the requested information, or null if problems occurred
	 */
	public static MatchDayDetails retrieveMatchDay(String md_id){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		MatchDayDetails ret = null;
		try {
			con = Helper.getConnection();

			String stm = "SELECT md.id, md.num, md.name, md.start_date, md.end_date, md.club, md.location, md.tournament_name, md.tournament_year, l.city AS lcity, l.name AS lname, c.name AS club_name " +
					"FROM lm.matchday AS md " +
					"INNER JOIN lm.club AS c ON c.id = md.club " +
					"INNER JOIN lm.location AS l ON l.id = md.location " +
					"WHERE md.id = ?";

			pst = con.prepareStatement(stm);
			pst.setString(1, md_id);
			rs = pst.executeQuery();
			rs.next();

			String id = rs.getString("id");
			int num = rs.getInt("num");
			String name = rs.getString("name");
			Date start_date = rs.getDate("start_date");
			Date end_date = rs.getDate("end_date");
			long club = rs.getLong("club");
			long location = rs.getLong("location");
			String tournamentName = rs.getString("tournament_name");
			int tournamentYear = rs.getInt("tournament_year");
			String organizer = rs.getString("club_name");
			String locationName =  rs.getString("lcity") + " - " +  rs.getString("lname");
			ret = new MatchDayDetails(new MatchDay(id, num, start_date, end_date, club, location, tournamentName, tournamentYear, name), organizer, locationName);

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}

	/**
	 * Retrieves a list of actions by a specific player
	 * @param playerId the id of the specified player
	 * @return a list of PlayerCareerEvent containing the requested info
	 */
	public static List<PlayerCareerEvent> retrievePlayerCareerEvents(long playerId){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<PlayerCareerEvent> ret = new ArrayList<PlayerCareerEvent>();

		try {
			con = Helper.getConnection();
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
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}

	
	/**
	 * Retrieves a set of lists of match results given a specific tournament.
	 * @param tournamentName the name of the tournament
	 * @param tournamentYear the year of the tournament
	 * @return a map with the match day indexes as keys and a list of the corresponding matches as the values
	 */
	public static Map<Integer, List<MatchUpResult>> retrieveMatchResultsFromTournament(String tournamentName, int tournamentYear){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		Map<Integer, List<MatchUpResult>> ret = null;
		try {
			con = Helper.getConnection();

			String stm = "SELECT tp.num, mu.id AS match_id, mu.match_day, md.start_date AS md_start_date, md.num AS md_num, mu.tournament_phase_name AS t_phase_name, mu.start_date, mu.start_time, mu.goals_host, mu.goals_guest, ch.id AS host_id, ch.name AS host_name, cg.id AS guest_id, cg.name AS guest_name " +
					"FROM lm.matchup AS mu " +
					"INNER JOIN lm.plays AS p ON p.match_id = mu.id " +
					"INNER JOIN lm.tournamentphase AS tp ON tp.name = mu.tournament_phase_name AND tp.tournament_name = mu.tournament_name AND mu.tournament_phase_year = tp.tournament_year " +
					"INNER JOIN lm.lineup AS luh ON luh.id = p.lineup_host " +
					"INNER JOIN lm.lineup AS lug ON lug.id = p.lineup_guest " +
					"INNER JOIN lm.club AS ch ON ch.id = luh.club_id " +
					"INNER JOIN lm.club AS cg ON cg.id = lug.club_id " +
					"INNER JOIN lm.matchday AS md ON md.id = mu.match_day " +
					"WHERE mu.tournament_name=? AND mu.tournament_phase_year=? " +
					"ORDER BY md.start_date, md.id, mu.start_date, mu.start_time ASC;";

			pst = con.prepareStatement(stm);
			pst.setString(1, tournamentName);
			pst.setInt(2, tournamentYear);
			rs = pst.executeQuery();
			if (!rs.isAfterLast()) {
				ret = new HashMap<Integer, List<MatchUpResult>>();
				while (rs.next()) {
					String matchUpID = rs.getString("match_id");
					String matchDayID = rs.getString("match_day");
					Date matchDayDate = rs.getDate("md_start_date");
					int matchDayNum = rs.getInt("md_num");
					String tournamentPhaseName = rs.getString("t_phase_name");
					int tournamentPhaseNum = rs.getInt("num");
					Date date = rs.getDate("start_date");
					int teamHostGoals = rs.getInt("goals_host");
					int teamGuestGoals = rs.getInt("goals_guest");
					Time time = rs.getTime("start_time");
					long clubHostID = rs.getLong("host_id");
					String teamHostName = rs.getString("host_name");
					long clubGuestID = rs.getLong("guest_id");
					String teamGuestName = rs.getString("guest_name");
					if (!ret.containsKey(matchDayNum)) { 
						ret.put(matchDayNum, new ArrayList<MatchUpResult>());
					}
					ret.get(matchDayNum).add(new MatchUpResult(matchUpID, matchDayID, matchDayDate, matchDayNum, tournamentPhaseName, tournamentPhaseNum, tournamentName, tournamentYear, clubHostID, clubGuestID, date, teamHostName, teamGuestName, teamHostGoals, teamGuestGoals, time));
				}
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}
	
	/**
	 * Retrieves a list of match days nested under the appropriate tournament
	 * @return a map with TournamentEssentials objects as indexes and lists of match days details as keys
	 */
	public static Map<TournamentEssentials, List<MatchDay>> retrieveAllMatchDays() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		Map<Integer, Map<String, TournamentEssentials>> tournaments = null;
		Map<TournamentEssentials, List<MatchDay>> ret = null;
		try {
			con = Helper.getConnection();

			String stm = "SELECT md.id, md.num AS md_num, md.name AS md_name, md.start_date, md.end_date, md.club, md.location, t.name AS t_name, t.year, t.num AS t_num " +
					"FROM lm.MatchDay as md " +
					"INNER JOIN lm.tournament AS t ON t.name = md.tournament_name and t.year = md.tournament_year " +
					"ORDER BY t.year DESC, t.num ASC, md.start_date DESC";

			pst = con.prepareStatement(stm);

			rs = pst.executeQuery();
			if (!rs.isAfterLast()) {
				ret = new HashMap<TournamentEssentials, List<MatchDay>>();
				tournaments = new HashMap<Integer, Map<String, TournamentEssentials>>();
				while (rs.next()) {
					String id = rs.getString("id");
					int mdNum = rs.getInt("md_num");
					Date startDate = rs.getDate("start_date");
					Date endDate = rs.getDate("end_date");
					long clubID = rs.getLong("club");
					String tournamentName = rs.getString("t_name");
					int tournamentYear = rs.getInt("year");
					int tournamentNum = rs.getInt("t_num");
					long locationID = rs.getLong("location");
					String mdName = rs.getString("md_name");
					if (! tournaments.containsKey(tournamentYear)){
						tournaments.put(tournamentYear, new HashMap<String, TournamentEssentials>());
					}
					if (! tournaments.get(tournamentYear).containsKey(tournamentName)){
						tournaments.get(tournamentYear).put(tournamentName, new TournamentEssentials(tournamentName, tournamentYear, tournamentNum));
					}
					if (! ret.containsKey(tournaments.get(tournamentYear).get(tournamentName))) {
						ret.put(tournaments.get(tournamentYear).get(tournamentName), new ArrayList<MatchDay>());
					}
					ret.get(tournaments.get(tournamentYear).get(tournamentName)).add(new MatchDay(id, mdNum, startDate, endDate, clubID, locationID, tournamentName, tournamentYear, mdName));
				}
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}

	/**
	 * Retrieve a list of all the locations
	 * @return a list of Location objects
	 */
	public static List<Location> retrieveAllLocations() {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<Location> ret = null;
		try {
			con = Helper.getConnection();

			String stm = "SELECT l.id, l.city, l.name " +
					"FROM lm.Location AS l";

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
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}
	
	/**
	 * Retrieves the list of player that are playing for the specified club, i.e. for which the end date of the ownership is set after the current date. 
	 * @param clubId the id of the required club
	 * @return the list of requested Player objects
	 */
	public static List<Player> retrievePlayersFromClub(long clubId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<Player> ret = null;
		try {
			con = Helper.getConnection();

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
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}
	
	/**
	 * Retrieves the list of managers for a specific club.
	 * @param clubId the id of the club
	 * @return the list of LMUser objects
	 */
	public static List<LMUser> retrieveManagersFromClub(long clubId) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<LMUser> ret = null;
		try {
			con = Helper.getConnection();

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
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}
	
	/**
	 * Retrieves the details for a specific club.
	 * @param clubId the club's id
	 * @return the details of the club in a ClubDetails object
	 */
	public static ClubDetails retrieveClubDetails(long clubId){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		ClubDetails ret = null;
		try {
			con = Helper.getConnection();

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
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
			}
		}
		return ret;
	}
	
	/**
	 * 
	 * @param eventId
	 * @return
	 */
	public static EventResult retrieveEventResult(long eventId){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		EventResult ret = null;
		try {
			
			con = Helper.getConnection();

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
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}
	
	/**
	 * Retrieves a collection of all the relevant information for a player.
	 * @param playerId the id of the requested player
	 * @return a PlayerCareerInfor containing the requested information
	 */
	public static PlayerCareerInfo retrievePlayerCareerInfo(long playerId){

		Connection con = null;
		String stm = null;
		PreparedStatement pst = null;
		ResultSet rs = null;
		Player playerData = null;

		try {
			con = Helper.getConnection();
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
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		List<OwnershipResult> ownerships = retrieveOwnershipsFromPlayer(playerId);
		List<PlayerCareerEvent> careerEvents = retrievePlayerCareerEvents(playerId);
		return new PlayerCareerInfo(playerData, ownerships, careerEvents);
	}

	private static List<MatchDayDetails> retrieveMatchDayDetailsFromTournament(
			String tournamentName, int tournamentYear){
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		List<MatchDayDetails> ret = null;
		try {
			con = Helper.getConnection();

			String stm = "SELECT md.id, md.num, md.name, md.start_date, md.end_date, md.club, md.location, c.name as club_name,l.id AS loc_id, l.name AS loc_name, l.city AS loc_city " +
					"FROM lm.MatchDay as md " +
					"LEFT JOIN lm.Club as c ON md.club=c.id " +
					"INNER JOIN lm.Location as l ON md.location=l.id " +
					"WHERE md.tournament_name=? AND md.tournament_year=? " +
					"ORDER BY md.start_date DESC";

			pst = con.prepareStatement(stm);
			pst.setString(1, tournamentName);
			pst.setLong(2, tournamentYear);
			rs = pst.executeQuery();
			if (!rs.isAfterLast()) {
				ret = new ArrayList<MatchDayDetails>();
				while (rs.next()) {
					String id = rs.getString("id");
					int num = rs.getInt("num");
					Date startDate = rs.getDate("start_date");
					Date endDate = rs.getDate("end_date");
					long clubID = rs.getLong("club");
					long locationID = rs.getLong("loc_id");
					String name = rs.getString("name");
					
					String organizerClubName = rs.getString("club_name");
					String locationName = rs.getString("loc_city") + " - " + rs.getString("loc_name");
					MatchDay matchDay = new MatchDay(id, num, startDate, endDate, clubID, locationID, tournamentName, tournamentYear, name);
					ret.add(new MatchDayDetails(matchDay, organizerClubName, locationName));
				}
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}

			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}
	
	/**
	 * Retrieves all the details of a specific tournament.
	 * @param tournamentName the name of the tournament
	 * @param tournamentYear the year of the tournament
	 * @return a TournamentDetails object containing the requested info, or null if problems occurred
	 */
	public static TournamentDetails retrieveTournamentDetails(
			String tournamentName, int tournamentYear) {
		Connection con = null;
		PreparedStatement pst = null;
		ResultSet rs = null;

		TournamentDetails ret = null;
		try {
			con = Helper.getConnection();

			String stm = "SELECT t.max_age, t.sex, t.organizer " +
					"FROM lm.Tournament as t " +
					"WHERE t.name=? AND t.year=?";

			pst = con.prepareStatement(stm);
			pst.setString(1, tournamentName);
			pst.setLong(2, tournamentYear);
			rs = pst.executeQuery();
			if (rs.next()) {
				int maxAge = rs.getInt("max_age");
				String sex = rs.getString("sex");
				String organizerEmail = rs.getString("organizer");
				
				Tournament tournament = new Tournament(tournamentName, tournamentYear, maxAge, sex, organizerEmail);
				List<MatchDayDetails> matchDayDetails = retrieveMatchDayDetailsFromTournament(tournamentName, tournamentYear);
				Map<Integer, List<MatchUpResult>> matchUpResults = retrieveMatchResultsFromTournament(tournamentName, tournamentYear);
				ret = new TournamentDetails(tournament, matchDayDetails, matchUpResults);
			}

		} catch (SQLException ex) {
			Logger lgr = Logger.getLogger(DML.class.getName());
			lgr.log(Level.WARNING, ex.getMessage(), ex);
			Notification.show("Connection Problem", ex.getMessage(),
					com.vaadin.ui.Notification.Type.ERROR_MESSAGE);

		} finally {

			try {
				if (rs != null) {
					rs.close();
				}
				if (pst != null) {
					pst.close();
				}
			} catch (SQLException ex) {
				Logger lgr = Logger.getLogger(DML.class.getName());
				lgr.log(Level.WARNING, ex.getMessage(), ex);
				Notification.show("Connection Problem", ex.getMessage(),
						com.vaadin.ui.Notification.Type.ERROR_MESSAGE);
			}
		}
		return ret;
	}
}