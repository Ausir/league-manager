package it.unipd.dei.db.kayak.league_manager.data;

import java.sql.Date;

public class Event {
	// CREATE TABLE lm.Event (
	// id BIGSERIAL,
	// match_id ID_REF,
	// editable BOOLEAN NOT NULL,
	// insertion_time DATE DEFAULT statement_timestamp() NOT NULL,
	// instant SMALLINT NOT NULL, -- from 0000 to 1000 -- TIME is a reserved SQL
	// keyword
	// fraction SMALLINT NOT NULL, --first/second half or overtimes
	// action varchar(30) NOT NULL,
	// ownership_id ID_REF NOT NULL,
	// secretary EMAIL NOT NULL,
	// PRIMARY KEY (id),
	// FOREIGN KEY (match_id) REFERENCES lm.MatchUp (id),
	// FOREIGN KEY (ownership_id) REFERENCES lm.Ownership (id),
	// FOREIGN KEY (secretary) REFERENCES lm.Secretary (user_email),
	// FOREIGN KEY (action) REFERENCES lm.Action (name)
	// );
	private long id;
	private long matchUpID;
	private boolean editable;
	private Date insertionTime;
	private int instant;
	private int fraction;
	private String action;
	private long ownershipID;
	private String secretaryEmail;

	public Event(long id, long matchUpID, boolean editable, Date insertionTime,
			int instant, int fraction, String action, long ownershipID,
			String secretaryEmail) {
		super();
		this.id = id;
		this.matchUpID = matchUpID;
		this.editable = editable;
		this.insertionTime = insertionTime;
		this.instant = instant;
		this.fraction = fraction;
		this.action = action;
		this.ownershipID = ownershipID;
		this.secretaryEmail = secretaryEmail;
	}

	public long getID() {
		return id;
	}

	public long getMatchUpID() {
		return matchUpID;
	}

	public boolean isEditable() {
		return editable;
	}

	public Date getInsertionTime() {
		return insertionTime;
	}

	public int getInstant() {
		return instant;
	}

	public int getFraction() {
		return fraction;
	}

	public String getAction() {
		return action;
	}

	public long getOwnershipID() {
		return ownershipID;
	}

	public String getSecretaryEmail() {
		return secretaryEmail;
	}
}
