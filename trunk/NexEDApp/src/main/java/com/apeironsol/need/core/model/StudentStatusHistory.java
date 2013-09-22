package com.apeironsol.need.core.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import com.apeironsol.framework.BaseEntity;

@Entity
@Table(name = "STUDENT_STATUS_HISTORY")
public class StudentStatusHistory extends BaseEntity {

	/**
	 * Universal serial version id for this class.
	 */
	private static final long	serialVersionUID	= -930692945844854656L;

	@NotNull(message = "model.action_taken_time_mandatory")
	@Column(name = "ACTION_TAKEN_TIME", nullable = false)
	private Timestamp			actionTakenTime;

	@Column(name = "ACTION", length = 255)
	private String				action;

	@Column(name = "ACTION_TAKEN_BY", length = 40)
	private String				actionTakenBy;

	@Column(name = "COMMENTS", length = 1000)
	private String				comments;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "STUDENT_ID", nullable = false)
	private Student				student;

	public String getActionTakenBy() {
		return this.actionTakenBy;
	}

	public void setActionTakenBy(final String actionTakenBy) {
		this.actionTakenBy = actionTakenBy;
	}

	public String getComments() {
		return this.comments;
	}

	public void setComments(final String comments) {
		this.comments = comments;
	}

	public Student getStudent() {
		return this.student;
	}

	public void setStudent(final Student student) {
		this.student = student;
	}

	public String getAction() {
		return this.action;
	}

	public void setAction(final String action) {
		this.action = action;
	}

	public Timestamp getActionTakenTime() {
		return this.actionTakenTime;
	}

	public void setActionTakenTime(final Timestamp actionTakenTime) {
		this.actionTakenTime = actionTakenTime;
	}

}
