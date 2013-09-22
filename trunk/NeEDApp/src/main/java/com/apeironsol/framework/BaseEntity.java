/**
 * This document is a part of the source code and related artifacts for
 * SMSystem.
 * www.apeironsol.com
 * Copyright © 2012 apeironsol
 * 
 */
package com.apeironsol.framework;

/**
 * class for base entity
 * 
 * @author Pradeep
 */
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Version;

import com.apeironsol.need.util.DateUtil;
import com.apeironsol.need.util.portal.ViewUtil;
import com.apeironsol.framework.exception.InvalidArgumentException;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= 2265755475839098520L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long				id;

	@Version
	@Column(name = "VERSION", nullable = false)
	private Long				version;

	@Column(name = "AUDIT_TIMESTAMP", nullable = false)
	private Timestamp			auditTimestamp;

	@Column(name = "AUDIT_USER_NAME", nullable = false)
	private String				auditUsername;

	public Long getId() {
		return this.id;
	}

	public Long getVersion() {
		return this.version;
	}

	public Timestamp getAuditTimestamp() {
		return this.auditTimestamp;
	}

	public void setAuditTimestamp(final Timestamp auditTimestamp) {
		this.auditTimestamp = auditTimestamp;
	}

	public String getAuditUsername() {
		return this.auditUsername;
	}

	public void setAuditUsername(final String auditUsername) {
		this.auditUsername = auditUsername;
	}

	@PrePersist
	public void prePersist() {

		// Audit time stamp
		this.auditTimestamp = new Timestamp(DateUtil.getSystemDate().getTime());

		if (this.auditUsername == null) {
			// Audit user name;
			this.auditUsername = ViewUtil.getPrincipal() == null ? "anonymous" : ViewUtil.getPrincipal();
		}
	}

	public void validate() throws InvalidArgumentException {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((this.id == null) ? 0 : this.id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (this.getClass() != obj.getClass()) {
			return false;
		}
		BaseEntity other = (BaseEntity) obj;
		if (this.id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!this.id.equals(other.id)) {
			return false;
		}
		return true;
	}

	/**
	 * Creates a clone of the given date. Returns null when the given date is
	 * null.
	 * 
	 * @param date
	 *            Date The date for which to create a clone.
	 * @return Date The clone of the given date. Null when the given date is
	 *         null.
	 */
	public Date cloneDate(final Date date) {
		return (date != null) ? (Date) date.clone() : null;
	}

	/**
	 * Creates a clone of the given time stamp. Returns null when the given time
	 * stamp is null.
	 * 
	 * @param timestamp
	 *            Time stamp the time stamp for which to create a clone.
	 * @return Time stamp The clone of the given time stamp. Null when the given
	 *         time stamp is null.
	 */
	public Timestamp cloneTimestamp(final Timestamp timestamp) {
		return (timestamp != null) ? (Timestamp) timestamp.clone() : null;
	}

}
